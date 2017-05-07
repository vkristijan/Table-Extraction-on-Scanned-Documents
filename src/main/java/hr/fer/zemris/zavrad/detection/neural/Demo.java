package hr.fer.zemris.zavrad.detection.neural;

import hr.fer.zemris.zavrad.detection.CornerDetection;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Demo {
    public static void main(String[] args) {
        CornerDetection cd = new CornerDetection();
        System.out.println(cd.getWeightCount());
    }
}
