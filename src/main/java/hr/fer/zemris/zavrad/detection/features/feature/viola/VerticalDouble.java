package hr.fer.zemris.zavrad.detection.features.feature.viola;

import hr.fer.zemris.zavrad.util.img.IntegralImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class VerticalDouble extends ViolaFeature {
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
}
