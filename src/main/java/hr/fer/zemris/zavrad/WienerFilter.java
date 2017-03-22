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

    double getLocalMean(GrayScaleImage image, int h, int w){
        double mean = 0;

        int width = image.getWidth();
        int height = image.getHeight();
        byte[][] data = image.getData();

        int fromH = h - n / 2;
        int toH = fromH + n;

        int fromW = w - m / 2;
        int toW = fromW + m;

        if (fromH < 0) fromH = 0;
        if (fromW < 0) fromW = 0;
        if (toH > height) toH = height;
        if (toW > width) toW = width;

        for (int i = fromH; i < toH; ++i){
            for (int j = fromW; j < toW; ++j){
                mean += (int)data[i][j] & 0xFF;
            }
        }
        mean /= (toH - fromH);
        mean /= (toW - fromW);

        return mean;
    }

    double getLocalVariance(GrayScaleImage image, int h, int w){
        double variance = 0;

        int width = image.getWidth();
        int height = image.getHeight();
        byte[][] data = image.getData();

        int fromH = h - n / 2;
        int toH = fromH + n;

        int fromW = w - m / 2;
        int toW = fromW + m;

        if (fromH < 0) fromH = 0;
        if (fromW < 0) fromW = 0;
        if (toH > height) toH = height;
        if (toW > width) toW = width;

        double mean = getLocalMean(image, h, w);
        for (int i = fromH; i < toH; ++i){
            for (int j = fromW; j < toW; ++j){
                int pixel = (int)data[i][j] & 0xFF;
                variance += pixel * pixel;
            }
        }
        variance /= (toW - fromW);
        variance /= (toH - fromH);
        variance -= mean * mean;

        return variance;
    }

    double getNoiseVariance(GrayScaleImage image){
        double noise = 0;

        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                noise += getLocalVariance(image, i, j);
            }
        }

        return noise / (width * height);
    }

    @Override
    public GrayScaleImage filter(GrayScaleImage image) {
        GrayScaleImage filtered = new GrayScaleImage(image.getWidth(), image.getHeight());

        int width = image.getWidth();
        int height = image.getHeight();
        byte[][] filteredData = filtered.getData();
        byte[][] data = image.getData();

        double noise = getNoiseVariance(image);
        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                double mean = getLocalMean(image, i, j);
                double variance = getLocalVariance(image, i, j);
                filteredData[i][j] = (byte)(mean + (variance/(variance + noise))*(((int)data[i][j] & 0xFF) - mean));
            }
        }

        return filtered;
    }
}
