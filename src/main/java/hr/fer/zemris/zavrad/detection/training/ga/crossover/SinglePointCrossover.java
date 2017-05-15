package hr.fer.zemris.zavrad.detection.training.ga.crossover;

import hr.fer.zemris.zavrad.detection.training.ga.Chromosome;
import hr.fer.zemris.zavrad.util.Rnd;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class SinglePointCrossover implements ICrossover {
    @Override
    public Chromosome crossover(Chromosome a, Chromosome b) {
        int n = a.size();

        double[] data = new double[n];
        int crossingPoint = Rnd.nextInt(n);

        double[] dataA = a.getData();
        double[] dataB = b.getData();
        System.arraycopy(dataA, 0, data, 0, crossingPoint);
        System.arraycopy(dataB, crossingPoint, data, crossingPoint, n - crossingPoint);

        return new Chromosome(data);
    }
}
