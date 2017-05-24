package hr.fer.zemris.zavrad.detection.features.feature.viola;

import hr.fer.zemris.zavrad.util.img.IntegralImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class HorizontalDouble extends ViolaFeature {
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
}
