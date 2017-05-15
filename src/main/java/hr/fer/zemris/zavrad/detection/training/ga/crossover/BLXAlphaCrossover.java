package hr.fer.zemris.zavrad.detection.training.ga.crossover;

import hr.fer.zemris.zavrad.detection.training.ga.Chromosome;
import hr.fer.zemris.zavrad.util.Rnd;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class BLXAlphaCrossover implements ICrossover {
    private double alpha;

    public BLXAlphaCrossover(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public Chromosome crossover(Chromosome a, Chromosome b) {
        int n = a.size();

        double[] data = new double[n];

        double[] dataA = a.getData();
        double[] dataB = b.getData();

        for (int i = 0; i < n; ++i){
            double min = Math.min(dataA[i], dataB[i]);
            double max = Math.max(dataA[i], dataB[i]);

            double delta = (max - min) * alpha;


            data[i] = Rnd.nextDouble(min - delta, max + delta);
        }

        return new Chromosome(data);
    }
}
