package hr.fer.zemris.zavrad.detection.features;

import hr.fer.zemris.zavrad.detection.features.feature.CompositeFeature;
import hr.fer.zemris.zavrad.detection.features.feature.DiagonalLineFeature;
import hr.fer.zemris.zavrad.detection.features.feature.HorizontalLineFeature;
import hr.fer.zemris.zavrad.detection.features.feature.VerticalLineFeature;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * Calculates the features for corner of a table by counting the number
 * of black pixels on the image. The pixels are counted on a HORIZONTAL_LINES
 * of horizontal lines, and on VERTICAL_LINES of vertical lines that are
 * equidistant. The pixels will also be counted on the two main diagonals, and
 * on four sub-diagonals.
 *
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CornerFeatureExtractor implements IFeatureExtractor {
    private static final double DISTANCE = 0.2;
    private static final int SKIP_STEP = 5;

    @Override
    public double[] getFeatures(GrayScaleImage img, int x, int y, int w, int h) {
        if (w != h){
            throw new IllegalArgumentException("The windows should be a square!");
        }

        CompositeFeature compositeFeature = new CompositeFeature();
        compositeFeature.addFeature(new HorizontalLineFeature(DISTANCE, SKIP_STEP));
        compositeFeature.addFeature(new VerticalLineFeature(DISTANCE, SKIP_STEP));
        compositeFeature.addFeature(new DiagonalLineFeature(SKIP_STEP));

        double[] features = new double[8];
        byte[][] data = img.getData();
        compositeFeature.getFeature(data, x, y, w, h, features, 0);

        return features;
    }
}
