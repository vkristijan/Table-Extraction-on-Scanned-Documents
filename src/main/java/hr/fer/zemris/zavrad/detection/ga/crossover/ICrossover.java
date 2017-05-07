package hr.fer.zemris.zavrad.detection.ga.crossover;

import hr.fer.zemris.zavrad.detection.ga.Chromosome;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface ICrossover {
    Chromosome crossover(Chromosome a, Chromosome b);
}
