package hr.fer.zemris.zavrad.detection.training;

import hr.fer.zemris.zavrad.detection.CornerDetection;
import hr.fer.zemris.zavrad.detection.features.CornerFeatureExtractor;
import hr.fer.zemris.zavrad.detection.features.IFeatureExtractor;
import hr.fer.zemris.zavrad.detection.ga.Chromosome;
import hr.fer.zemris.zavrad.detection.ga.Ga;
import hr.fer.zemris.zavrad.detection.ga.crossover.BLXAlphaCrossover;
import hr.fer.zemris.zavrad.detection.ga.crossover.ICrossover;
import hr.fer.zemris.zavrad.detection.ga.crossover.SinglePointCrossover;
import hr.fer.zemris.zavrad.detection.ga.evaluator.CornerEvaluator;
import hr.fer.zemris.zavrad.detection.ga.evaluator.IEvaluator;
import hr.fer.zemris.zavrad.detection.ga.mutation.IMutation;
import hr.fer.zemris.zavrad.detection.ga.mutation.RandomMutation;
import hr.fer.zemris.zavrad.detection.ga.selection.ISelection;
import hr.fer.zemris.zavrad.detection.ga.selection.RandomSelection;
import hr.fer.zemris.zavrad.detection.ga.selection.TournamentSelection;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CornerDetectionTraining {
    private static final int TOURNAMENT_SIZE = 3;
    private static final double MUTATION_RATE = 0.5;
    private static final double MUTATION_CHANCE = 0.05;
    private static final double ALPHA = 0.25;

    public static void main(String[] args) {
        Path dataPath = Paths.get(args[0]);
        CornerDetection detection = new CornerDetection();
        IFeatureExtractor extractor = new CornerFeatureExtractor();

        int chromosomeSize = detection.getWeightCount();

        ISelection firstSelection = new RandomSelection();
        ISelection secondSelection = new TournamentSelection(TOURNAMENT_SIZE);
        ICrossover crossover = new BLXAlphaCrossover(ALPHA);
        IMutation mutation = new RandomMutation(MUTATION_RATE, MUTATION_CHANCE);
        IEvaluator evaluator = new CornerEvaluator(dataPath, detection, extractor);

        Ga ga = new Ga(firstSelection, secondSelection, crossover, mutation, evaluator, chromosomeSize);
        Chromosome best = ga.run();

        evaluator.showError(best);

        Path outputPath = Paths.get(args[1]);
        detection.setWeights(best.getData());
        detection.writeWeightsToFile(outputPath);
    }
}
