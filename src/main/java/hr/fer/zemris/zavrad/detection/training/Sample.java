package hr.fer.zemris.zavrad.detection.training;

import hr.fer.zemris.zavrad.table.CornerValue;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Sample {
    public CornerValue value;
    public double[] input;

    public Sample(CornerValue value, double[] input) {
        this.value = value;
        this.input = input;
    }
}
