package hr.fer.zemris.zavrad.detection.ga.evaluator;

import hr.fer.zemris.zavrad.detection.ga.Chromosome;

import java.nio.file.Path;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CornerEvaluator implements IEvaluator {
    private Path dataPath;

    public CornerEvaluator(Path dataPath) {
        this.dataPath = dataPath;
    }

    @Override
    public void evaluate(Chromosome chromosome) {
        double fitness = 0;



        chromosome.setFitness(fitness);
    }
}
