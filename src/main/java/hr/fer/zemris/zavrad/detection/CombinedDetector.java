package hr.fer.zemris.zavrad.detection;

import hr.fer.zemris.zavrad.detection.adaboost.AdaBoost;
import hr.fer.zemris.zavrad.detection.features.DistanceFeatureExtractor;
import hr.fer.zemris.zavrad.detection.features.IFeatureExtractor;
import hr.fer.zemris.zavrad.table.Corner;
import hr.fer.zemris.zavrad.table.CornerValue;
import hr.fer.zemris.zavrad.table.Table;
import hr.fer.zemris.zavrad.util.Point;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.IntegralImage;
import hr.fer.zemris.zavrad.util.img.draw.Geometry;
import hr.fer.zemris.zavrad.util.img.filters.ImageFilter;
import hr.fer.zemris.zavrad.util.img.filters.threshold.ThresholdBinarization;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CombinedDetector {
    private int windowSize = 75;
    private int step = 10;

    private AdaBoost adaBoost;
    private IFeatureExtractor extractor;
    private CornerDetection detection;

    public CombinedDetector(AdaBoost adaBoost, IFeatureExtractor extractor, CornerDetection detection) {
        this.adaBoost = adaBoost;
        this.extractor = extractor;
        this.detection = detection;
    }

    public List<Corner> detect(GrayScaleImage img){
        List<Corner> pointsOfInterest = adaDetect(img);
        List<Corner> corners = neuralDetect(img, pointsOfInterest);

        return corners;
    }

    public List<Corner> detect(GrayScaleImage img, List<Corner> oldCorners){
        List<Corner> corners = adaDetect(img, oldCorners);

        return corners;
    }

    private List<Corner> neuralDetect(GrayScaleImage img, List<Corner> pointsOfInterest) {
        List<Corner> corners = new ArrayList<>();
        IntegralImage integralImage = IntegralImage.fromGrayscaleImage(img);

        for (Corner point : pointsOfInterest){
            int x = point.getPosition().getX() - windowSize / 2;
            int y = point.getPosition().getY() - windowSize / 2;

            double[] features = extractor.getFeatures(integralImage, x, y, windowSize, windowSize);
            CornerValue value = detection.detect(features);
            if (value.equals(CornerValue.NONE)) continue;

            Corner corner = new Corner(value, point.getPosition());
            corners.add(corner);
        }

        return corners;
    }

    private List<Corner> adaDetect(GrayScaleImage img) {
        IntegralImage integralImage;
        if (img instanceof IntegralImage){
            integralImage = (IntegralImage)img;
        } else {
            integralImage = IntegralImage.fromGrayscaleImage(img);
        }

        List<Corner> corners = new ArrayList<>();
        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 1; y < height - windowSize; y += step){
            for (int x = 1; x < width - windowSize; x += step){

                if (adaBoost.classify(integralImage, x, y, windowSize, windowSize) == 0) continue;

                Point position = new Point(x + windowSize / 2, y + windowSize / 2);
                Corner corner = new Corner(CornerValue.CENTER, position);
                corners.add(corner);
            }
        }
        return corners;
    }

    private List<Corner> adaDetect(GrayScaleImage img, List<Corner> oldCorners) {
        IntegralImage integralImage;
        if (img instanceof IntegralImage){
            integralImage = (IntegralImage)img;
        } else {
            integralImage = IntegralImage.fromGrayscaleImage(img);
        }

        List<Corner> corners = new ArrayList<>();
        int width = img.getWidth();
        int height = img.getHeight();

        for (Corner old : oldCorners){
            int oldX = old.getPosition().getX() - windowSize/2;
            int oldY = old.getPosition().getY() - windowSize/2;
            for (int y = oldY - windowSize/2; y < oldY + windowSize/2; y += step){
                for (int x = oldX - windowSize/2; x < oldX + windowSize/2; x += step){
                    if (x <= 0|| x >= width - windowSize) continue;
                    if (y <= 0 || y >= height - windowSize) continue;

                    if (adaBoost.classify(integralImage, x, y, windowSize, windowSize) == 0) continue;

                    Point position = new Point(x + windowSize / 2, y + windowSize / 2);
                    Corner corner = new Corner(CornerValue.CENTER, position);
                    corners.add(corner);
                }
            }
        }
        return corners;
    }

    /**
     * Runs the detector on a test case given in the argument and produces an image
     * result with the detected values.
     *
     * Expected arguments are:
     * <ul>
     *     <li>Path to the input image</li>
     *     <li>Path to the output image</li>
     *     <li>Path to the classifier data for ada boost</li>
     *     <li>Path to the weights for the neural network</li>
     * </ul>
     *
     * @param args command line arguments
     * @throws IOException in case of an error during reading files
     */
    public static void main(String[] args) throws IOException {
        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        Path classifierData = Paths.get(args[2]);
        Path weights = Paths.get(args[3]);

        AdaBoost adaBoost = AdaBoost.readFromFile(classifierData);
        IFeatureExtractor extractor = new DistanceFeatureExtractor();
        CornerDetection detection = new CornerDetection();
        detection.readWeightsFromFile(weights);

        CombinedDetector detector = new CombinedDetector(adaBoost, extractor, detection);

        GrayScaleImage img = GrayScaleImage.load(input.toFile());
        ImageFilter filter = new ThresholdBinarization(127);
        img = filter.filter(img);

        long start_time = System.nanoTime();
        List<Corner> corners = detector.detect(img);
        long end_time = System.nanoTime();
        double difference = (end_time - start_time)/1e6;
        System.out.println("Detection time: " + difference + "ms");

        System.out.println(corners.size());
        Table table = new Table(corners, img);

        img.save(output.toFile());
    }
}
