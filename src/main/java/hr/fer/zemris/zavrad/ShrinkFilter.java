package hr.fer.zemris.zavrad;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class ShrinkFilter implements ImageFilter {
    private int n;
    private double k;

    public ShrinkFilter(int n, double k) {
        this.n = n;
        this.k = k;
    }

    @Override
    public GrayScaleImage filter(GrayScaleImage... images) {
        if (images.length != 1){
            throw new ImageFilterException("Shrink filter expects exactly one argument!");
        }

        GrayScaleImage image = images[0];
        int width = image.getWidth();
        int height = image.getHeight();

        GrayScaleImage filtered = new GrayScaleImage(width, height);
        byte[][] filteredData = filtered.getData();
        byte[][] data = image.getData();

        int threshold = (int)(n * n * k);
        for (int i = 0; i < height; ++i){
            for (int j = 0; j < width; ++j){
                filteredData[i][j] = data[i][j];
                if (data[i][j] != 0) continue;

                int p = getPixelCount(image, i, j, n);
                if (p > threshold){
                    filteredData[i][j] = (byte)255;
                }
            }
        }
        return filtered;
    }

    private int getPixelCount(GrayScaleImage image, int x, int y, int n) {
        int width = image.getWidth();
        int height = image.getHeight();
        byte[][] data = image.getData();

        int count = 0;

        int fromX = x - n / 2;
        int toX = fromX + n;

        int fromY = y - n / 2;
        int toY = fromY + n;

        if (fromX < 0) fromX = 0;
        if (fromY < 0) fromY = 0;
        if (toX > height) toX = height;
        if (toY > width) toY = width;

        for (int i = fromX; i < toX; ++i){
            for (int j = fromY; j < toY; ++j){
                if (((int)data[i][j] & 0xFF) == 255){
                    count++;
                }
            }
        }

        return count;
    }
}
