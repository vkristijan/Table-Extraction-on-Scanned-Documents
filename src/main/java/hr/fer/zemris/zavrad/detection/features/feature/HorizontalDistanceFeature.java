package hr.fer.zemris.zavrad.detection.features.feature;

import hr.fer.zemris.zavrad.util.img.GrayScaleImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class HorizontalDistanceFeature implements IFeature {
    private double lineStart;

    public HorizontalDistanceFeature(double lineStart) {
        this.lineStart = lineStart;
    }

    @Override
    public int getFeature(GrayScaleImage img, int x, int y, int w, int h, double[] features, int index) {
        byte[][] data = img.getData();
        features[index] = 1;
        int yCor = (int)(h * lineStart) + y;

        for (int xCor = x; xCor < x + w; ++xCor){
            if (data[yCor][xCor] == GrayScaleImage.BLACK
                    && (xCor + 1 >= data[0].length
                    || data[yCor][xCor + 1] == GrayScaleImage.BLACK)){

                features[index] = (double)(xCor - x) / (double)w;
                break;
            }
        }

        index++;
        features[index] = 1;
        for (int xCor = x + w - 1; xCor >= x; --xCor){
            if (data[yCor][xCor] == GrayScaleImage.BLACK
                    && (xCor - 1 < 0
                    || data[yCor][xCor - 1] == GrayScaleImage.BLACK)){

                features[index] = (double)(w - xCor + x) / (double)w;
                break;
            }
        }

        return 2;
    }
}
