package hr.fer.zemris.zavrad;

import hr.fer.zemris.zavrad.detection.CombinedDetector;
import hr.fer.zemris.zavrad.detection.CornerDetection;
import hr.fer.zemris.zavrad.detection.adaboost.AdaBoost;
import hr.fer.zemris.zavrad.detection.features.DistanceFeatureExtractor;
import hr.fer.zemris.zavrad.detection.features.IFeatureExtractor;
import hr.fer.zemris.zavrad.detection.training.Sample;
import hr.fer.zemris.zavrad.table.Corner;
import hr.fer.zemris.zavrad.table.Table;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.filters.ImageFilter;
import hr.fer.zemris.zavrad.util.img.filters.threshold.ThresholdBinarization;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class MultipleDetection {
    public static void main(String[] args) throws IOException {
        Path in = Paths.get(args[0]);
        Path out = Paths.get(args[1]);
        Path classifierData = Paths.get(args[2]);
        Path weights = Paths.get(args[3]);

        AdaBoost adaBoost = AdaBoost.readFromFile(classifierData);
        IFeatureExtractor extractor = new DistanceFeatureExtractor();
        CornerDetection detection = new CornerDetection();
        detection.readWeightsFromFile(weights);

        CombinedDetector detector = new CombinedDetector(adaBoost, extractor, detection);

        double totalTime = 0;

        File[] files = in.toFile().listFiles();
        if (files == null) return;
        int index = 0;
        List<Corner> oldCorners = null;
        for (File input : files){
            GrayScaleImage img = GrayScaleImage.load(input);

            long start_time = System.nanoTime();
            ImageFilter filter = new ThresholdBinarization(127);
            img = filter.filter(img);

            List<Corner> corners;
            if (oldCorners != null){
                corners = detector.detect(img, oldCorners);
            } else {
                corners = detector.detect(img);
            }

            Table table = new Table(corners, img);
            if (oldCorners == null){
                oldCorners = table.getCorners();
            }

            long end_time = System.nanoTime();
            double difference = (end_time - start_time)/1e6;
            totalTime += difference;
            Path output = out.resolve(index + ".png");
            img.save(output.toFile());
            index++;
        }


        System.out.println("Detection time: " + totalTime + "ms");
    }
}
