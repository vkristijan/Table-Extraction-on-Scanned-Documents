package hr.fer.zemris.zavrad.detection.features.feature;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class DiagonalLineFeature implements IFeature {
    private int skipStep;

    public DiagonalLineFeature(int skipStep) {
        this.skipStep = skipStep;
    }

    @Override
    public int getFeature(GrayScaleImage img, int x, int y, int w, int h, double[] features, int index) {
        byte[][] data = img.getData();
        int length = h * 2 / 3;

        features[index] = 0;
        int xCor = x;
        int yCor = y + length;
        for (int i = 0; i < length - 1; ++i){
            if (data[yCor - i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += skipStep;
            }
        }

        index++;
        features[index] = 0;
        xCor = x + w - length;
        yCor = y + h - 1;
        for (int i = 0; i < length - 1; ++i){
            if (data[yCor - i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += skipStep;
            }
        }

        index++;
        features[index] = 0;
        xCor = x;
        yCor = y + h - length;
        for (int i = 0; i < length - 1; ++i){
            if (data[yCor + i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += skipStep;
            }
        }

        index++;
        features[index] = 0;
        xCor = x + w - length;
        yCor = y;
        for (int i = 0; i < length - 1; ++i){
            if (data[yCor + i][xCor + i] == GrayScaleImage.BLACK){
                features[index]++;
                i += skipStep;
            }
        }

        return 4;
    }
}
