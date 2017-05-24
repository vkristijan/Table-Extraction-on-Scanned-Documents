package hr.fer.zemris.zavrad.detection.features;

import hr.fer.zemris.zavrad.detection.features.feature.*;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class DistanceFeatureExtractor implements IFeatureExtractor {
    @Override
    public double[] getFeatures(GrayScaleImage img, int x, int y, int w, int h) {
        if (w != h){
            throw new IllegalArgumentException("The windows should be a square!");
        }

        CompositeFeature compositeFeature = new CompositeFeature();
        compositeFeature.addFeature(new HorizontalDistanceFeature(0.1));
        compositeFeature.addFeature(new HorizontalDistanceFeature(0.9));

        compositeFeature.addFeature(new VerticalDistanceFeature(0.1));
        compositeFeature.addFeature(new VerticalDistanceFeature(0.9));

        compositeFeature.addFeature(new HorizontalLineFeature(0.35, 5));
        compositeFeature.addFeature(new VerticalLineFeature(0.35, 5));
        compositeFeature.addFeature(new DiagonalLineFeature(5));

        double[] features = new double[16];
        compositeFeature.getFeature(img, x, y, w, h, features, 0);

        return features;
    }
}
