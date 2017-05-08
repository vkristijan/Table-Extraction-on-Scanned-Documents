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
    private static final int HORIZONTAL_LINES = 2;
    private static final int VERTICAL_LINES = 2;
    private static final double DISTANCE = 0.2;

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
        features[index] = 0;
        int yCor = y + (int)(h * DISTANCE);

        for (int xCor = x; xCor < x + w; ++xCor){
            //data[yCor][xCor] = 0;
            if (data[yCor][xCor] == GrayScaleImage.BLACK){
                features[index]++;
                xCor += SKIP_STEP;
            }
        }

        index++;
        features[index] = 0;
        yCor = y + h - (int)(h * DISTANCE);

        for (int xCor = x; xCor < x + w; ++xCor){
            //data[yCor][xCor] = 0;
            if (data[yCor][xCor] == GrayScaleImage.BLACK){
                features[index]++;
                xCor += SKIP_STEP;
            }
        }
    }

    private void calculateVerticalLines(byte[][] data, int x, int y, int w, int h, double[] features) {
        int index = 0;
        features[index] = 0;
        int xCor = x + (int)(w * DISTANCE);

        for (int yCor = y; yCor < y + h; ++yCor){
            //data[yCor][xCor] = 0;
            if (data[yCor][xCor] == GrayScaleImage.BLACK){
                features[index]++;
                yCor += SKIP_STEP;
            }
        }

        index++;
        features[index] = 0;
        xCor = x + w - (int)(w * DISTANCE);

        for (int yCor = y; yCor < y + h; ++yCor){
            //data[yCor][xCor] = 0;
            if (data[yCor][xCor] == GrayScaleImage.BLACK){
                features[index]++;
                yCor += SKIP_STEP;
            }
        }
    }

    private void calculateDiagonalLines(byte[][] data, int x, int y, int w, int h, double[] features) {
        int length = h * 2 / 3;

        int index = HORIZONTAL_LINES + VERTICAL_LINES;
        features[index] = 0;
        int xCor = x;
        int yCor = y + length;
        for (int i = 0; i < length - 1; ++i){
            //data[yCor - i][xCor + i] = 0;
            if (data[yCor - i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }

        index++;
        features[index] = 0;
        xCor = x + w - length;
        yCor = y + h - 1;
        for (int i = 0; i < length - 1; ++i){
            //data[yCor - i][xCor + i] = 0;
            if (data[yCor - i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }

        index++;
        features[index] = 0;
        xCor = x;
        yCor = y + h - length;
        for (int i = 0; i < length - 1; ++i){
            //data[yCor + i][xCor + i] = 0;
            if (data[yCor + i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }

        index++;
        features[index] = 0;
        xCor = x + w - length;
        yCor = y;
        for (int i = 0; i < length - 1; ++i){
            //data[yCor + i][xCor + i] = 0;
            if (data[yCor + i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += SKIP_STEP;
            }
        }
    }

    private int numberOfFeatures() {
        //4 diagonals
        return HORIZONTAL_LINES + VERTICAL_LINES + 4;
    }
}
