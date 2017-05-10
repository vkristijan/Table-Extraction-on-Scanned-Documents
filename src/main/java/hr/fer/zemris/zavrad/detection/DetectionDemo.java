package hr.fer.zemris.zavrad.detection;

import hr.fer.zemris.zavrad.detection.features.CornerFeatureExtractor;
import hr.fer.zemris.zavrad.detection.features.IFeatureExtractor;
import hr.fer.zemris.zavrad.table.Corner;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
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

        IFeatureExtractor extractor = new CornerFeatureExtractor();
        SlidingWindow slider = new SlidingWindow(windowSize, step, detection, extractor);

        GrayScaleImage img = GrayScaleImage.load(input.toFile());
        ImageFilter filter = new ThresholdBinarization(127);
        img = filter.filter(img);

        List<Corner> corners = slider.detectCorners(img);

        System.out.println(corners.size());
        for (Corner corner : corners){
            int x = corner.getPosition().getX() - windowSize / 2;
            int y = corner.getPosition().getY() - windowSize / 2;
            Geometry.drawSquare(img, x, y, windowSize);
        }

        img.save(output.toFile());
    }
}
