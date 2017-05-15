package hr.fer.zemris.zavrad.detection.training.ga;

import hr.fer.zemris.zavrad.util.Rnd;

/**
 * A double array chromosome used in the genetic algorithm.
 * Provides all the necessary methods to change the data.
 *
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Chromosome implements Comparable<Chromosome>{
    private static final double LOWER_BOUND = -1;
    private static final double UPPER_BOUND = 1;

    private double[] data;
    private double fitness;

    public Chromosome(double[] data) {
        this.data = data;
    }

    public Chromosome(int size){
        data = new double[size];

        for (int i = 0; i < size; ++i){
            data[i] = Rnd.nextDouble(LOWER_BOUND, UPPER_BOUND);
        }
    }

    public double[] getData(){
        return data;
    }

    public void setData(double[] data){
        this.data = data;
    }

    public int size(){
        return data.length;
    }

    public void set(int index, double value){
        data[index] = value;
    }

    public double get(int index){
        return data[index];
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Chromosome o) {
        return Double.compare(this.fitness, o.fitness);
    }
}
