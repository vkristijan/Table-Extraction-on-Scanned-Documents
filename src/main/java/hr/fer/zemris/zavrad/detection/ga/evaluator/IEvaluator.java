package hr.fer.zemris.zavrad.detection.ga.evaluator;

import hr.fer.zemris.zavrad.detection.ga.Chromosome;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface IEvaluator {
    IEvaluator copy();

    void evaluate(Chromosome chromosome);

    void showError(Chromosome chromosome);
}
