package hr.fer.zemris.zavrad.detection.training.ga.crossover;

import hr.fer.zemris.zavrad.detection.training.ga.Chromosome;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface ICrossover {
    Chromosome crossover(Chromosome a, Chromosome b);
}
