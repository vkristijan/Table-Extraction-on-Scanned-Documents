package hr.fer.zemris.zavrad.detection.features.feature;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface IFeature {
    int getFeature(byte[][] data, int x, int y, int w, int h, double[] features, int index);
}
