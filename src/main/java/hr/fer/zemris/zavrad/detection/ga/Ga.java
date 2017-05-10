package hr.fer.zemris.zavrad.detection.ga;

import hr.fer.zemris.zavrad.detection.ga.crossover.ICrossover;
import hr.fer.zemris.zavrad.detection.ga.evaluator.IEvaluator;
import hr.fer.zemris.zavrad.detection.ga.mutation.IMutation;
import hr.fer.zemris.zavrad.detection.ga.selection.ISelection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Ga {
    private static final int POPULATION_SIZE = 60;
    private static final int MAX_GENERATIONS = 25000;
    private static final double STOP_CONDITION = 0.999;

    private ISelection firstSelection;
    private ISelection secondSelection;
    private ICrossover crossover;
    private IMutation mutation;
    private ThreadLocal<IEvaluator> evaluator;

    private int chromosomeSize;

    public Ga(ISelection firstSelection, ISelection secondSelection,
              ICrossover crossover, IMutation mutation, IEvaluator evaluator,
              int chromosomeSize) {
        this.firstSelection = firstSelection;
        this.secondSelection = secondSelection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.chromosomeSize = chromosomeSize;

        this.evaluator = ThreadLocal.withInitial(evaluator::copy);
    }

    public Chromosome run(){
        ExecutorService pool = null;
        List<Chromosome> population = null;

        try {
            pool = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors(),
                    r -> {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        return t;
                    });

            population = initPopulation();
            evaluate(population, pool);
            Chromosome best = bestOfPopulation(population);

            int generation = 0;
            while (generation < MAX_GENERATIONS && best.getFitness() < STOP_CONDITION){
                generation++;
                List<Chromosome> newPopulation = new ArrayList<>();
                newPopulation.add(best);

                for (int i = 0; i < POPULATION_SIZE; ++i){
                    Chromosome parent1 = firstSelection.select(population);
                    Chromosome parent2 = secondSelection.select(population);

                    Chromosome child = crossover.crossover(parent1, parent2);
                    mutation.mutate(child);
                    newPopulation.add(child);
                }
                evaluate(newPopulation, pool);

                population = newPopulation;
                best = bestOfPopulation(population);
                System.out.println("Iteration #" + generation + "  - Best fitness: " + best.getFitness());
                populationStats(population);
            }

        } finally {
            if (pool != null){
                pool.shutdown();
            }
        }

        return bestOfPopulation(population);
    }

    private void evaluate(List<Chromosome> population, ExecutorService pool) {
        List<Callable<Void>> callables = new ArrayList<>();
        population.forEach(c -> callables.add(() -> evaluateSolution(c)));

        try {
            List<Future<Void>> results = pool.invokeAll(callables);

            for (Future<Void> result : results) {
                result.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Void evaluateSolution(Chromosome c) {
        evaluator.get().evaluate(c);
        return null;
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
            population.add(chromosome);
        }
        return population;
    }
}
