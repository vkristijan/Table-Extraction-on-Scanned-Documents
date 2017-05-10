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
        compositeFeature.addFeature(new HorizontalDistanceFeature(0.3));
        compositeFeature.addFeature(new HorizontalDistanceFeature(0.7));
        compositeFeature.addFeature(new HorizontalDistanceFeature(0.9));

        compositeFeature.addFeature(new VerticalDistanceFeature(0.1));
        compositeFeature.addFeature(new VerticalDistanceFeature(0.3));
        compositeFeature.addFeature(new VerticalDistanceFeature(0.7));
        compositeFeature.addFeature(new VerticalDistanceFeature(0.9));

        double[] features = new double[16];
        byte[][] data = img.getData();
        compositeFeature.getFeature(data, x, y, w, h, features, 0);

        return features;
    }
}
