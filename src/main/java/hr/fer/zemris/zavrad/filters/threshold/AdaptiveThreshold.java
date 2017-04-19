package hr.fer.zemris.zavrad.filters.threshold;

import hr.fer.zemris.zavrad.GrayScaleImage;
import hr.fer.zemris.zavrad.filters.ImageFilter;
import hr.fer.zemris.zavrad.filters.ImageFilterException;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class AdaptiveThreshold implements ImageFilter {
    private double q;

    public AdaptiveThreshold(double q) {
        this.q = q;
    }

    @Override
    public GrayScaleImage filter(GrayScaleImage ... images) {
        if (images.length != 3){
            throw new ImageFilterException("Adaptive threshold filter expects 3 arguments!");
        }
        GrayScaleImage img1 = images[0];
        GrayScaleImage img2 = images[1];
        GrayScaleImage img3 = images[2];

        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()){
            throw new ImageFilterException("The given images are not the same dimensions!");
        }
        if (img1.getWidth() != img3.getWidth() || img1.getHeight() != img3.getHeight()){
            throw new ImageFilterException("The given images are not the same dimensions!");
        }

        int width = img1.getWidth();
        int height = img1.getHeight();

        GrayScaleImage result = new GrayScaleImage(width, height);

        byte[][] resultData = result.getData();
        byte[][] data1 = img1.getData();
        byte[][] data2 = img2.getData();

        int delta = getDelta(img1, img2, img3);
        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                int x = (((int)data1[i][j] & 0xFF) - ((int)data2[i][j] & 0xFF));
                if (x > delta){
                    resultData[i][j] = 0;
                } else {
                    resultData[i][j] = (byte)255;
                }
            }
        }

        return result;
    }

    private int getDelta(GrayScaleImage background, GrayScaleImage source, GrayScaleImage niblack) {
        byte[][] backgroundData = background.getData();
        byte[][] sourceData = source.getData();
        byte[][] niblackData = niblack.getData();

        int width = source.getWidth();
        int height = source.getHeight();

        double a = 0;
        double b = 0;

        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                if (((int)niblackData[i][j] & 0xFF) == 0){
                    a += (((int)backgroundData[i][j] & 0xFF) - ((int)sourceData[i][j] & 0xFF));
                    b++;
                }
            }
        }
        return (int)(q * a / b);
    }
}
