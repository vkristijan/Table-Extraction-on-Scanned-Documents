package hr.fer.zemris.zavrad.filters.misc;

import hr.fer.zemris.zavrad.GrayScaleImage;
import hr.fer.zemris.zavrad.filters.ImageFilter;
import hr.fer.zemris.zavrad.filters.ImageFilterException;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class LimitedSwell implements ImageFilter {
    private int n;
    private double k;
    private int dx;
    private int dy;

    public LimitedSwell(int n, double k, int dx, int dy) {
        this.n = n;
        this.k = k;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public GrayScaleImage filter(GrayScaleImage... images) {
        if (images.length != 1){
            throw new ImageFilterException("Swell filter expects exactly one argument!");
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
                if (((int)data[i][j] & 0xFF) != 255) continue;

                imageStats stats = getPixelCount(image, i, j, n);
                if (stats.count > threshold){
                    int xAvg = stats.xSum / stats.count;
                    int yAvg = stats.ySum / stats.count;

                    if (Math.abs(xAvg - i) < dx && Math.abs(yAvg - j) < dy){
                        filteredData[i][j] = (byte)0;
                    }
                }
            }
        }
        return filtered;
    }

    private imageStats getPixelCount(GrayScaleImage image, int x, int y, int n) {
        int width = image.getWidth();
        int height = image.getHeight();
        byte[][] data = image.getData();

        imageStats stats = new imageStats();

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
                if (((int)data[i][j] & 0xFF) == 0){
                    stats.add(i, j);
                }
            }
        }

        return stats;
    }

    class imageStats{
        int count;
        int xSum;
        int ySum;

        void add(int x, int y){
            xSum += x;
            ySum += y;
            count++;
        }
    }
}
