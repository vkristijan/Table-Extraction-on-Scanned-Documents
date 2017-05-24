package hr.fer.zemris.zavrad.detection.features.feature;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface IFeature {
    int getFeature(GrayScaleImage img, int x, int y, int w, int h, double[] features, int index);
}
