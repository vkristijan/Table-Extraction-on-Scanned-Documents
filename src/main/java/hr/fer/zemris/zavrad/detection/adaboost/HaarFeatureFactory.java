package hr.fer.zemris.zavrad.detection.adaboost;

import hr.fer.zemris.zavrad.detection.features.feature.haar.*;
import hr.fer.zemris.zavrad.util.Rnd;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class HaarFeatureFactory {
    public static HaarFeature getRandomFeature(){
        int x = Rnd.nextInt(4);
        switch (x){
            case 0:
                return getHorizontalDouble();
            case 1:
                return getVerticalDouble();
            case 2:
                return getHorizontalTriple();
            case 3:
                return getVerticalTriple();
            default:
                return null;
        }
    }

    private static HaarFeature getHorizontalDouble() {
        HelperData data = new HelperData();
        return new HorizontalDouble(
                data.startWidth,
                data.startHeight,
                data.endWidth,
                data.endHeight,
                data.center1
        );
    }

    private static HaarFeature getVerticalDouble() {
        HelperData data = new HelperData();
        return new VerticalDouble(
                data.startWidth,
                data.startHeight,
                data.endWidth,
                data.endHeight,
                data.center1
        );
    }

    private static HaarFeature getHorizontalTriple() {
        HelperData data = new HelperData();
        return new HorizontalTriple(
                data.startWidth,
                data.startHeight,
                data.endWidth,
                data.endHeight,
                data.center1,
                data.center2
        );
    }

    private static HaarFeature getVerticalTriple() {
        HelperData data = new HelperData();
        return new VerticalTriple(
                data.startWidth,
                data.startHeight,
                data.endWidth,
                data.endHeight,
                data.center1,
                data.center2
        );
    }

    private static class HelperData {
        private double startWidth;
        private double startHeight;
        private double endWidth;
        private double endHeight;

        private double center1;
        private double center2;

        public HelperData() {
            startWidth = Rnd.nextDouble();
            endWidth = Rnd.nextDouble();
            if (endWidth < startWidth){
                double tmp = startWidth;
                startWidth = endWidth;
                endWidth = tmp;
            }

            startHeight = Rnd.nextDouble();
            endHeight = Rnd.nextDouble();
            if (endHeight < startHeight){
                double tmp = startHeight;
                startHeight = endHeight;
                endHeight = tmp;
            }

            center1 = Rnd.nextDouble();
            center2 = Rnd.nextDouble();
            if (center1 > center2){
                double tmp = center1;
                center1 = center2;
                center2 = tmp;
            }
        }
    }
}
