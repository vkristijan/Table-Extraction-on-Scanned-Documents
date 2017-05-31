package hr.fer.zemris.zavrad.detection.features;

import hr.fer.zemris.zavrad.detection.features.feature.*;
import hr.fer.zemris.zavrad.detection.features.feature.haar.HorizontalTriple;
import hr.fer.zemris.zavrad.detection.features.feature.haar.VerticalTriple;
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
        compositeFeature.addFeature(new HorizontalLineFeature(0.25, 5));
        compositeFeature.addFeature(new VerticalLineFeature(0.25, 5));
        compositeFeature.addFeature(new DiagonalLineFeature(5));

        compositeFeature.addFeature(new HorizontalTriple(0.1, 0.2, 0.9, 0.8, 0.33, 0.66));
        compositeFeature.addFeature(new VerticalTriple(0.2, 0.1, 0.8, 0.9, 0.33, 0.66));

        compositeFeature.addFeature(new HorizontalTriple(0.1, 0.35, 0.9, 0.65, 0.3, 0.7));
        compositeFeature.addFeature(new VerticalTriple(0.35, 0.1, 0.65, 0.9, 0.3, 0.7));

        double[] features = new double[24];
        compositeFeature.getFeature(img, x, y, w, h, features, 0);

        return features;
    }
}
