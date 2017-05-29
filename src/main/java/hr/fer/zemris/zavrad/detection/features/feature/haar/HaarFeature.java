package hr.fer.zemris.zavrad.detection.features.feature.haar;

import hr.fer.zemris.zavrad.detection.features.feature.IFeature;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.IntegralImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public abstract class HaarFeature implements IFeature {
    private double startWidth;
    private double startHeight;
    private double endWidth;
    private double endHeight;

    public HaarFeature(double startWidth, double startHeight, double endWidth, double endHeight) {
        this.startWidth = startWidth;
        this.startHeight = startHeight;
        this.endWidth = endWidth;
        this.endHeight = endHeight;
    }

    @Override
    public int getFeature(GrayScaleImage img, int x, int y, int w, int h, double[] features, int index) {
        if (!(img instanceof IntegralImage)){
            throw new IllegalArgumentException("Viola features require an integral image!");
        }

        IntegralImage iImg = (IntegralImage)img;
        features[index] = getFeature(iImg, x, y, w, h);

        return 1;
    }

    public double getFeature(IntegralImage img, int x, int y, int w, int h) {
        int startW = x + (int) (startWidth * w);
        int startH = y + (int) (startHeight * h);
        int endW = x + (int) (endWidth * w);
        int endH = y + (int) (endHeight * h);

        int dW = endW - startW;
        int dH = endH - startH;
        int size = dW * dH;

        if (size == 0) return 0;
        return  ((double)calculateFeature(img, startW, startH, endW, endH)) / size;
    }

    protected abstract int calculateFeature(IntegralImage img, int startW, int startH, int endW, int endH);

    @Override
    public String toString() {
        return startWidth + " " + startHeight + " "
                + endWidth + " " + endHeight;
    }

    public static HaarFeature fromString(String string) {
        String[] strings = string.split(" ");

        double startWidth = Double.parseDouble(strings[0]);
        double startHeight = Double.parseDouble(strings[1]);
        double endWidth = Double.parseDouble(strings[2]);
        double endHeight = Double.parseDouble(strings[3]);

        if (strings[4].trim().equals("HD")){
            return HorizontalDouble.fromString(startWidth, startHeight, endWidth, endHeight, strings);
        } else if (strings[4].trim().equals("HT")){
            return HorizontalTriple.fromString(startWidth, startHeight, endWidth, endHeight, strings);
        } else if (strings[4].trim().equals("VD")){
            return VerticalDouble.fromString(startWidth, startHeight, endWidth, endHeight, strings);
        } else if (strings[4].trim().equals("VT")){
            return VerticalTriple.fromString(startWidth, startHeight, endWidth, endHeight, strings);
        }

        return null;
    }
}
