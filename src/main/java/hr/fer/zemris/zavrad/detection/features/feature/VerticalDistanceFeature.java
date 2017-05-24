package hr.fer.zemris.zavrad.detection.features.feature;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class VerticalDistanceFeature implements IFeature {
    private double lineStart;

    public VerticalDistanceFeature(double lineStart) {
        this.lineStart = lineStart;
    }

    @Override
    public int getFeature(GrayScaleImage img, int x, int y, int w, int h, double[] features, int index) {
        byte[][] data = img.getData();
        features[index] = 1;
        int xCor = x + (int)(w * lineStart);

        for (int yCor = y; yCor < y + h; ++yCor){
            if (data[yCor][xCor] == GrayScaleImage.BLACK
                    && (yCor + 1 >= data.length
                    || data[yCor + 1][xCor] == GrayScaleImage.BLACK)){

                features[index] = (double)(yCor - y) / (double)h;
                break;
            }
        }

        index++;
        features[index] = 1;
        for (int yCor = y + h - 1; yCor >= y; --yCor){
            if (data[yCor][xCor] == GrayScaleImage.BLACK
                    && (yCor - 1 < 0
                    || data[yCor - 1][xCor] == GrayScaleImage.BLACK)){

                features[index] = (double)(h - yCor + y) / (double)h;
                break;
            }
        }

        return 2;
    }
}
