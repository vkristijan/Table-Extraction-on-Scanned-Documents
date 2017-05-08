package hr.fer.zemris.zavrad.detection.ga;

import hr.fer.zemris.zavrad.detection.ga.crossover.ICrossover;
import hr.fer.zemris.zavrad.detection.ga.evaluator.IEvaluator;
import hr.fer.zemris.zavrad.detection.ga.mutation.IMutation;
import hr.fer.zemris.zavrad.detection.ga.selection.ISelection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Ga {
    private static final int POPULATION_SIZE = 60;
    private static final int MAX_GENERATIONS = 100000;
    private static final double STOP_CONDITION = 0.999;

    private ISelection firstSelection;
    private ISelection secondSelection;
    private ICrossover crossover;
    private IMutation mutation;
    private IEvaluator evaluator;

    private int chromosomeSize;

    public Ga(ISelection firstSelection, ISelection secondSelection,
              ICrossover crossover, IMutation mutation, IEvaluator evaluator,
              int chromosomeSize) {
        this.firstSelection = firstSelection;
        this.secondSelection = secondSelection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.evaluator = evaluator;
        this.chromosomeSize = chromosomeSize;
    }

    public Chromosome run(){
        List<Chromosome> population = initPopulation();
        Chromosome best = bestOfPopulation(population);

        int generation = 0;
        while (generation < MAX_GENERATIONS && best.getFitness() < STOP_CONDITION){
            generation++;
            List<Chromosome> newPopulation = new ArrayList<>();
            newPopulation.add(best);

            for (int i = 0; i < POPULATION_SIZE; ++i){
                Chromosome parrent1 = firstSelection.select(population);
                Chromosome parrent2 = secondSelection.select(population);

                Chromosome child = crossover.crossover(parrent1, parrent2);
                mutation.mutate(child);
                evaluator.evaluate(child);
                newPopulation.add(child);
            }

            population = newPopulation;
            best = bestOfPopulation(population);
            System.out.println("Iteration #" + generation + "  - Best fitness: " + best.getFitness());
            populationStats(population);
        }

        return best;
    }

    private void populationStats(List<Chromosome> population) {
        double average = 0;
        double min = 1;

        for (Chromosome chromosome : population){
            average += chromosome.getFitness();
            if (min > chromosome.getFitness()){
                min = chromosome.getFitness();
            }
        }
        average /= population.size();

        System.out.println("Average fitness: " + average);
        System.out.println("Worst fitness: " + min);
    }

    private Chromosome bestOfPopulation(List<Chromosome> population) {
        Chromosome best = population.get(0);

        for (Chromosome chromosome : population){
            if (best.compareTo(chromosome) < 0){
                best = chromosome;
            }
        }

        return best;
    }

    private List<Chromosome> initPopulation() {
        List<Chromosome> population = new ArrayList<>();

        for (int i = 0; i < POPULATION_SIZE; ++i){
            Chromosome chromosome = new Chromosome(chromosomeSize);
            evaluator.evaluate(chromosome);

            population.add(chromosome);
        }
        return population;
    }
}
