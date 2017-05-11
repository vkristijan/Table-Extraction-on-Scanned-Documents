package hr.fer.zemris.zavrad.detection.sampling;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

import java.io.IOException;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface ISampling {
    void getSamples(GrayScaleImage image) throws IOException;
}
