package hr.fer.zemris.zavrad.filters.threshold;

import hr.fer.zemris.zavrad.GrayScaleImage;
import hr.fer.zemris.zavrad.filters.ImageFilter;
import hr.fer.zemris.zavrad.filters.ImageFilterException;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class ThresholdBinarization implements ImageFilter {
    private int threshold;

    public ThresholdBinarization(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public GrayScaleImage filter(GrayScaleImage... images) {
        if (images.length != 1){
            throw new ImageFilterException("Threshold binarization filter expects 1 argument1!");
        }
        GrayScaleImage img = images[0];
        byte[][] data = img.getData();
        int width = img.getWidth();
        int height = img.getHeight();
        GrayScaleImage result = new GrayScaleImage(width, height);

        byte[][] newData = result.getData();
        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                if (((int)data[i][j] & 0xFF) < threshold){
                    newData[i][j] = (byte)0;
                } else {
                    newData[i][j] = (byte)255;
                }
            }
        }

        return result;
    }
}
