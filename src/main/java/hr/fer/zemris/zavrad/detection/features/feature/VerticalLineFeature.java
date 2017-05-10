package hr.fer.zemris.zavrad.detection.features.feature;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class VerticalLineFeature implements IFeature {
    private double distance;
    private int skipStep;

    public VerticalLineFeature(double distance, int skipStep) {
        this.distance = distance;
        this.skipStep = skipStep;
    }

    @Override
    public int getFeature(byte[][] data, int x, int y, int w, int h, double[] features, int index) {
        features[index] = 0;
        int xCor = x + (int)(w * distance);

        for (int yCor = y; yCor < y + h; ++yCor){
            if (data[yCor][xCor] == GrayScaleImage.BLACK){
                features[index]++;
                yCor += skipStep;
            }
        }

        index++;
        features[index] = 0;
        xCor = x + w - (int)(w * distance);

        for (int yCor = y; yCor < y + h; ++yCor){
            if (data[yCor][xCor] == GrayScaleImage.BLACK){
                features[index]++;
                yCor += skipStep;
            }
        }

        return 2;
    }
}
