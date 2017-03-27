package hr.fer.zemris.zavrad;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface Interpolator {
    GrayScaleImage interpolate(GrayScaleImage img1, GrayScaleImage img2);
}
