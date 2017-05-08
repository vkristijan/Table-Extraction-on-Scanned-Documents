package hr.fer.zemris.zavrad.detection.features;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * Calculates the features for corner of a table by counting the number
 * of black pixels on the image. The pixels are counted on a HORIZONTAL_LINES
 * of horizontal lines, and on VERTICAL_LINES of vertical lines that are
 * equidistant. The pixels will also be counted on the two main diagonals, and
 * on four sub-diagonals.
 *
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CornerFeatureExtractor implements IFeatureExtractor {
    private static final int HORIZONTAL_LINES = 5;
    private static final int VERTICAL_LINES = 5;

    private static final int SKIP_STEP = 5;

    @Override
    public double[] getFeatures(GrayScaleImage img) {
        return getFeatures(img, 0, 0, img.getWidth(), img.getHeight());
    }

    @Override
    public double[] getFeatures(GrayScaleImage img, int x, int y, int w, int h) {
        if (w != h){
            throw new IllegalArgumentException("The windows should be a square!");
        }

        double[] features = new double[numberOfFeatures()];

        byte[][] data = img.getData();
        calculateVerticalLines(data, x, y, w, h, features);
        calculateHorizontalLines(data, x, y, w, h, features);
        calculateDiagonalLines(data, x, y, w, h, features);

        return features;
    }

    private void calculateHorizontalLines(byte[][] data, int x, int y, int w, int h, double[] features) {
        int index = VERTICAL_LINES;
        for (int i = 0; i < HORIZONTAL_LINES; ++i){
            features[index + i] = 0;
            int yCor = y + (h * (i + 1)) / (HORIZONTAL_LINES + 1);

            for (int xCor = x; xCor < x + w; ++xCor){
                if (data[yCor][xCor] == GrayScaleImage.BLACK){
                    features[index + i]++;
                    xCor += SKIP_STEP;
                }
            }
        }
    }

    private void calculateVerticalLines(byte[][] data, int x, int y, int w, int h, double[] features) {
        for (int i = 0; i < VERTICAL_LINES; ++i){
            features[i] = 0;
            int xCor = x + (w * (i + 1)) / (VERTICAL_LINES + 1);

            for (int yCor = y; yCor < y + h; ++yCor){
                if (data[yCor][xCor] == GrayScaleImage.BLACK){
                    features[i]++;
                    yCor += SKIP_STEP;
                }
            }
        }
    }

    private void calculateDiagonalLines(byte[][] data, int x, int y, int w, int h, double[] features) {
        //main diagonal
        int index = HORIZONTAL_LINES + VERTICAL_LINES;
        features[index] = 0;
        for (int i = 0; i < w; ++i){
            if (data[y + i][x + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }

        //other diagonal
        index++;
        features[index] = 0;
        for (int i = 0; i < w; ++i){
            if (data[y + i][x + w - 1- i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }

        index++;
        features[index] = 0;
        int xCor = x + w / 2;
        int yCor = y;
        for (int i = 0; i < w / 2 - 1; ++i){
            if (data[yCor + i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }

        index++;
        features[index] = 0;
        xCor = x;
        yCor = y + h / 2;
        for (int i = 0; i < h / 2 - 1; ++i){
            if (data[yCor + i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }

        index++;
        features[index] = 0;
        xCor = x;
        yCor = y + h / 2;
        for (int i = 0; i < h / 2 - 1; ++i){
            if (data[yCor - i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }

        index++;
        features[index] = 0;
        xCor = x + w / 2;
        yCor = y + h - 1;
        for (int i = 0; i < h / 2 - 1; ++i){
            if (data[yCor - i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }
    }

    private int numberOfFeatures() {
        //2 diagonals + 4 other diagonals
        return HORIZONTAL_LINES + VERTICAL_LINES + 6;
    }
}
