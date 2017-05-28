package hr.fer.zemris.zavrad.detection.features.feature.haar;

import hr.fer.zemris.zavrad.util.img.IntegralImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class VerticalDouble extends HaarFeature {
    private double center;

    public VerticalDouble(double startWidth, double startHeight, double endWidth, double endHeight, double center) {
        super(startWidth, startHeight, endWidth, endHeight);
        this.center = center;
    }

    @Override
    protected int calculateFeature(IntegralImage img, int startW, int startH, int endW, int endH) {
        int mid = (int) (startH + center * (endH - startH));

        int black = img.getSum(startW, startH, endW, mid);
        int white = img.getSum(startW, mid, endW, endH);

        return  black - white;
    }

    public static HaarFeature fromString(
            double startWidth, double startHeight, double endWidth, double endHeight, String[] strings) {

        double center = Double.parseDouble(strings[5]);
        return new VerticalDouble(startWidth, startHeight, endWidth, endHeight, center);
    }

    @Override
    public String toString() {
        return super.toString() + " "
                + "VD " + center;
    }
}
