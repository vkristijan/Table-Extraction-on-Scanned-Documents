package hr.fer.zemris.zavrad.detection.ga.selection;

import hr.fer.zemris.zavrad.detection.ga.Chromosome;

import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public interface ISelection {
    Chromosome select(List<Chromosome> population);
}
