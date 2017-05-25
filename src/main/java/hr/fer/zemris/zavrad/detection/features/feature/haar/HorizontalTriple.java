package hr.fer.zemris.zavrad.detection.features.feature.haar;

import hr.fer.zemris.zavrad.util.img.IntegralImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class HorizontalTriple extends HaarFeature {
    private double center1;
    private double center2;

    public HorizontalTriple(double startWidth, double startHeight, double endWidth, double endHeight, double center1, double center2) {
        super(startWidth, startHeight, endWidth, endHeight);
        this.center1 = center1;
        this.center2 = center2;
    }

    @Override
    protected int calculateFeature(IntegralImage img, int startW, int startH, int endW, int endH) {
        int mid1 = (int) (startW + center1 * (endW - startW));
        int mid2 = (int) (startW + center2 * (endW - startW));

        int white = img.getSum(startW, startH, mid1, endH);
        white += img.getSum(mid2, startH, endW, endH);

        int black = img.getSum(mid1, startH, mid2, endH);

        return  black - white;
    }
}
