package hr.fer.zemris.zavrad.detection.ga.mutation;

import hr.fer.zemris.zavrad.detection.ga.Chromosome;
import hr.fer.zemris.zavrad.util.Rnd;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class RandomMutation implements IMutation {
    private double mutationRate;

    public RandomMutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        int index = Rnd.nextInt(chromosome.size());
        double delta = Rnd.nextDouble(-mutationRate, mutationRate);

        double value = chromosome.get(index);
        chromosome.set(index, value + delta);
    }
}
