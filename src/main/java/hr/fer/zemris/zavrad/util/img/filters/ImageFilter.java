package hr.fer.zemris.zavrad.util.img.filters;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface ImageFilter {
    GrayScaleImage filter(GrayScaleImage ... images);
}
