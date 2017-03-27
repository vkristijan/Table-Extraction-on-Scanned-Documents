package hr.fer.zemris.zavrad;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface ImageFilter {
    GrayScaleImage filter(GrayScaleImage ... images);
}
