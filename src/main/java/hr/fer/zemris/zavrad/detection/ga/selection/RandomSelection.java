package hr.fer.zemris.zavrad.detection.ga.selection;

import hr.fer.zemris.zavrad.detection.ga.Chromosome;
import hr.fer.zemris.zavrad.util.Rnd;

import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class RandomSelection implements ISelection {
    @Override
    public Chromosome select(List<Chromosome> population) {
        int index = Rnd.nextInt(population.size());
        return population.get(index);
    }
}
