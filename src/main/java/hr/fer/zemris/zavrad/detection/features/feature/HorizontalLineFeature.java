package hr.fer.zemris.zavrad.detection.features.feature;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class HorizontalLineFeature implements IFeature {
    private double distance;
    private int skipStep;

    public HorizontalLineFeature(double distance, int skipStep) {
        this.distance = distance;
        this.skipStep = skipStep;
    }

    @Override
    public int getFeature(byte[][] data, int x, int y, int w, int h, double[] features, int index) {
        features[index] = 0;
        int yCor = (int)(h * distance) + y;

        for (int xCor = x; xCor < x + w; ++xCor){
            if (data[yCor][xCor] == GrayScaleImage.BLACK){
                features[index]++;
                xCor += skipStep;
            }
        }

        index++;
        features[index] = 0;
        yCor = h - (int)(h * distance) + y;

        for (int xCor = x; xCor < x + w; ++xCor){
            if (data[yCor][xCor] == GrayScaleImage.BLACK){
                features[index]++;
                xCor += skipStep;
            }
        }

        return 2;
    }
}
