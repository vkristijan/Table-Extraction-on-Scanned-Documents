package hr.fer.zemris.zavrad.detection.features.feature.haar;

import hr.fer.zemris.zavrad.util.img.IntegralImage;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class VerticalTriple extends HaarFeature {
    private double center1;
    private double center2;

    public VerticalTriple(double startWidth, double startHeight, double endWidth, double endHeight, double center1, double center2) {
        super(startWidth, startHeight, endWidth, endHeight);
        this.center1 = center1;
        this.center2 = center2;
    }

    @Override
    protected int calculateFeature(IntegralImage img, int startW, int startH, int endW, int endH) {
        int mid1 = (int) (startH + center1 * (endH - startH));
        int mid2 = (int) (startH + center2 * (endH - startH));

        int black = img.getSum(startW, mid1, endW, mid2);

        int white = img.getSum(startW, startH, endW, mid1);
        white += img.getSum(startW, mid2, endW, endH);


        return  black - white;
    }

    public static HaarFeature fromString(
            double startWidth, double startHeight, double endWidth, double endHeight, String[] strings) {

        double center1 = Double.parseDouble(strings[5]);
        double center2 = Double.parseDouble(strings[6]);

        return new VerticalTriple(startWidth, startHeight, endWidth, endHeight, center1, center2);
    }

    @Override
    public String toString() {
        return super.toString() + " "
                + "VT " + center1 + " " + center2;
    }
}
