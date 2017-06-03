package hr.fer.zemris.zavrad.detection;

import hr.fer.zemris.zavrad.detection.features.DistanceFeatureExtractor;
import hr.fer.zemris.zavrad.detection.features.IFeatureExtractor;
import hr.fer.zemris.zavrad.table.Corner;
import hr.fer.zemris.zavrad.table.CornerValue;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.IntegralImage;
import hr.fer.zemris.zavrad.util.img.draw.Geometry;
import hr.fer.zemris.zavrad.util.img.filters.ImageFilter;
import hr.fer.zemris.zavrad.util.img.filters.threshold.ThresholdBinarization;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class DetectionDemo {
    public static void main(String[] args) throws IOException {
        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        Path weights = Paths.get(args[2]);

        int windowSize = 75;
        int step = 10;

        CornerDetection detection = new CornerDetection();
        detection.readWeightsFromFile(weights);

        IFeatureExtractor extractor = new DistanceFeatureExtractor();
        SlidingWindow slider = new SlidingWindow(windowSize, step, detection, extractor);

        GrayScaleImage img = GrayScaleImage.load(input.toFile());
        ImageFilter filter = new ThresholdBinarization(127);
        img = filter.filter(img);

        IntegralImage integralImage = IntegralImage.fromGrayscaleImage(img);

        long start_time = System.nanoTime();
        List<Corner> corners = slider.detectCorners(integralImage);
        long end_time = System.nanoTime();
        double difference = (end_time - start_time)/1e6;
        System.out.println("Detection time: " + difference + "ms");


        System.out.println(corners.size());
        for (Corner corner : corners){
            //if (corner.getValue() != CornerValue.UL_CORNER) continue;
            int x = corner.getPosition().getX();
            int y = corner.getPosition().getY();

            for (int ii = 0; ii < 9; ++ii){
                try {
                    Geometry.drawSquare(integralImage, x - ii, y - ii, 2 * ii);
                } catch (Exception ignored){
                }
            }
        }

        img.save(output.toFile());
    }
}
