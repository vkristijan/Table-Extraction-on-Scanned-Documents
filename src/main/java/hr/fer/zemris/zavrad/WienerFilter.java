package hr.fer.zemris.zavrad;

/**
 * Implementation of a <a href="https://en.wikipedia.org/wiki/Wiener_filter">
 * Wiener filter</a> that can be used to reduce noise from an grayscale image.
 *
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class WienerFilter implements ImageFilter {
    /**
     * Width of the sliding window.
     */
    private int n;
    /**
     * Height of the sliding window.
     */
    private int m;

    public WienerFilter(int n, int m) {
        this.n = n;
        this.m = m;
    }

    @Override
    public GrayScaleImage filter(GrayScaleImage ... images) {
        if (images.length != 1){
            throw new ImageFilterException("Wiener filter accepts only 1 argument!");
        }

        GrayScaleImage image = images[0];
        int width = image.getWidth();
        int height = image.getHeight();

        GrayScaleImage filtered = new GrayScaleImage(width, height);
        byte[][] filteredData = filtered.getData();
        byte[][] data = image.getData();

        double noise = image.getNoiseVariance(n, m);
        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                double mean = image.getLocalMean(i, j, n, m);
                double variance = image.getLocalVariance(i, j, n, m);
                filteredData[i][j] = (byte)(mean + (variance/(variance + noise))*(((int)data[i][j] & 0xFF) - mean));
            }
        }

        return filtered;
    }
}
