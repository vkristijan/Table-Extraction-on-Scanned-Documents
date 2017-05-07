package hr.fer.zemris.zavrad.detection.ga.mutation;

import hr.fer.zemris.zavrad.detection.ga.Chromosome;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface IMutation {
    void mutate(Chromosome chromosome);
}
