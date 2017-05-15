package hr.fer.zemris.zavrad.detection.neural;

import java.util.function.Function;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public enum ActivationFunction {
    STEP(x -> x < 0 ?  0. : 1.),
    LINEAR(x -> x),
    SIGMOID(x -> 1 / (1 + Math.pow(Math.E, -x))),
    HYP_TAN(Math::tanh);

    private Function<Double, Double> function;

    ActivationFunction(Function<Double, Double> function) {
        this.function = function;
    }

    public double valueAt(double x){
        return function.apply(x);
    }
}
