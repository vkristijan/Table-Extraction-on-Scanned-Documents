package hr.fer.zemris.zavrad.filters;

import hr.fer.zemris.zavrad.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface ImageFilter {
    GrayScaleImage filter(GrayScaleImage ... images);
}
