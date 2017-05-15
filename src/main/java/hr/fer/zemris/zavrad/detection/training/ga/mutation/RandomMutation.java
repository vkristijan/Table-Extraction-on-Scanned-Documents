package hr.fer.zemris.zavrad.detection.training.ga.mutation;

import hr.fer.zemris.zavrad.detection.training.ga.Chromosome;
import hr.fer.zemris.zavrad.util.Rnd;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class RandomMutation implements IMutation {
    private double mutationRate;
    private double mutationChance;

    public RandomMutation(double mutationRate, double mutationChance) {
        this.mutationRate = mutationRate;
        this.mutationChance = mutationChance;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        for (int i = 0; i < chromosome.size(); ++i){
            if (Rnd.nextDouble() >= mutationChance) continue;

            double delta = Rnd.nextGaussian() * mutationRate;
            double value = chromosome.get(i);
            chromosome.set(i, value + delta);
        }
    }
}
