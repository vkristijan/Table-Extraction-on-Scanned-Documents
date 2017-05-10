package hr.fer.zemris.zavrad.detection.features;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * Returns a double array containing the values of the features used as the input
 * layer of a neural network for image classification.
 *
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface IFeatureExtractor {
    /**
     * Generates the feature array using the whole image.
     *
     * @param img The source image.
     * @return An array containing all the features.
     */
    default double[] getFeatures(GrayScaleImage img){
        return getFeatures(img, 0, 0, img.getWidth(), img.getHeight());
    }

    /**
     * Generates the feature array using only a subset of the image, that is defined
     * with the starting <code>x</code> and <code>y</code> coordinates, and the width
     * and height of the subset.
     *
     * @param img The source image.
     * @param x The starting x coordinate.
     * @param y The starting y coordinate.
     * @param w The width of the window.
     * @param h The height of the window.
     * @return An array containing all the features.
     */
    double[] getFeatures(GrayScaleImage img, int x, int y, int w, int h);
}
