package hr.fer.zemris.zavrad.detection.training.ga.mutation;

import hr.fer.zemris.zavrad.detection.training.ga.Chromosome;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface IMutation {
    void mutate(Chromosome chromosome);
}
