package hr.fer.zemris.zavrad.detection;

import hr.fer.zemris.zavrad.detection.adaboost.AdaBoost;
import hr.fer.zemris.zavrad.detection.features.DistanceFeatureExtractor;
import hr.fer.zemris.zavrad.detection.features.IFeatureExtractor;
import hr.fer.zemris.zavrad.detection.features.feature.haar.HorizontalDouble;
import hr.fer.zemris.zavrad.detection.neural.NeuralException;
import hr.fer.zemris.zavrad.table.Corner;
import hr.fer.zemris.zavrad.table.CornerValue;
import hr.fer.zemris.zavrad.util.Point;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.IntegralImage;
import hr.fer.zemris.zavrad.util.img.draw.Geometry;
import hr.fer.zemris.zavrad.util.img.filters.ImageFilter;
import hr.fer.zemris.zavrad.util.img.filters.threshold.ThresholdBinarization;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class AdaDetection {
    private static int windowSize = 75;
    private static int step = 10;
    private static AdaBoost adaBoost;

    public static void main(String[] args) throws IOException {
        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        Path classifierData = Paths.get(args[2]);

        adaBoost = AdaBoost.readFromFile(classifierData);

        GrayScaleImage img = GrayScaleImage.load(input.toFile());
        ImageFilter filter = new ThresholdBinarization(127);
        img = filter.filter(img);

        IntegralImage integralImage = IntegralImage.fromGrayscaleImage(img);

        HorizontalDouble hd = new HorizontalDouble(0.4273, 0.2883, 0.544625, 0.49435, 0.3);
        hd.getFeature(integralImage, 0, 0, img.getWidth(), img.getHeight());

        List<Corner> corners = detectCorners(integralImage);

        System.out.println(corners.size());
        for (Corner corner : corners){
            int x = corner.getPosition().getX();
            int y = corner.getPosition().getY();

            for (int ii = 0; ii < 8; ++ii){
                try {
                    Geometry.drawSquare(integralImage, x - ii, y - ii, 2 * ii);
                } catch (Exception e){
                    continue;
                }
            }
        }

        integralImage.save(output.toFile());
    }

    private static List<Corner> detectCorners(IntegralImage img) {
        List<Corner> corners = new ArrayList<>();

        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 1; y < height - windowSize; y += step){
            for (int x = 1; x < width - windowSize; x += step){

                if (adaBoost.classify(img, x, y, windowSize, windowSize) == 0) continue;

                Point position = new Point(x + windowSize / 2, y + windowSize / 2);
                Corner corner = new Corner(CornerValue.CENTER, position);
                corners.add(corner);
            }
        }

        return corners;
    }
}
