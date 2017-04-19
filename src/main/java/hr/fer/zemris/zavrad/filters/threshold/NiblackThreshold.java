package hr.fer.zemris.zavrad.filters.threshold;

import hr.fer.zemris.zavrad.GrayScaleImage;
import hr.fer.zemris.zavrad.filters.ImageFilter;
import hr.fer.zemris.zavrad.filters.ImageFilterException;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class NiblackThreshold implements ImageFilter {
    /**
     * Width of the sliding window.
     */
    private int n;
    /**
     * Height of the sliding window.
     */
    private int m;
    /**
     * Determines how much of the boundary is taken as a part of the object.
     */
    private double k;

    public NiblackThreshold(int n, int m, double k) {
        this.n = n;
        this.m = m;
        this.k = k;
    }

    @Override
    public GrayScaleImage filter(GrayScaleImage ... images) {
        if (images.length != 1){
            throw new ImageFilterException("Niblack threshold accepts only 1 argument!");
        }

        GrayScaleImage image = images[0];
        int width = image.getWidth();
        int height = image.getHeight();

        GrayScaleImage filtered = new GrayScaleImage(width, height);

        byte[][] filteredData = filtered.getData();
        byte[][] data = image.getData();

        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                double mean = image.getLocalMean(i, j, n, m);
                double variance = Math.sqrt(image.getLocalVariance(i, j, n, m));

                double t = mean + k * variance;
                if (((int)data[i][j] & 0xFF) < t){
                    filteredData[i][j] = (byte)0;
                } else {
                    filteredData[i][j] = (byte)255;
                }
            }
        }

        return filtered;
    }
}
