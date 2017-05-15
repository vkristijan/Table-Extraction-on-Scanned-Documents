package hr.fer.zemris.zavrad.detection.training.ga.selection;

import hr.fer.zemris.zavrad.detection.training.ga.Chromosome;
import hr.fer.zemris.zavrad.util.Rnd;

import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class TournamentSelection implements ISelection {
    private int tournamentSize;

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Chromosome select(List<Chromosome> population) {
        Chromosome best = null;

        for (int i = 0; i < tournamentSize; ++i){
            int index = Rnd.nextInt(population.size());
            Chromosome chromosome = population.get(index);

            if (best == null || chromosome.getFitness() > best.getFitness()){
                best = chromosome;
            }
        }

        return best;
    }
}
