package hr.fer.zemris.zavrad.detection.features.feature.haar;

import hr.fer.zemris.zavrad.util.img.IntegralImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class HorizontalDouble extends HaarFeature {
    private double center;

    public HorizontalDouble(double startWidth, double startHeight, double endWidth, double endHeight, double center) {
        super(startWidth, startHeight, endWidth, endHeight);
        this.center = center;
    }

    @Override
    protected int calculateFeature(IntegralImage img, int startW, int startH, int endW, int endH) {
        int mid = (int) (startW + center * (endW - startW));

        int white = img.getSum(startW, startH, mid, endH);
        int black = img.getSum(mid, startH, endW, endH);

        return  black - white;
    }

    public static HaarFeature fromString(
            double startWidth, double startHeight, double endWidth, double endHeight, String[] strings) {

        double center = Double.parseDouble(strings[5]);
        return new HorizontalDouble(startWidth, startHeight, endWidth, endHeight, center);
    }

    @Override
    public String toString() {
        return super.toString() + " "
                + "HD " + center;
    }
}
