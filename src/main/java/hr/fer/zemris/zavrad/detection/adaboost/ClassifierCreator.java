package hr.fer.zemris.zavrad.detection.adaboost;

import hr.fer.zemris.zavrad.detection.features.feature.haar.HaarFeature;
import hr.fer.zemris.zavrad.util.Rnd;
import hr.fer.zemris.zavrad.util.img.IntegralImage;

import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class ClassifierCreator {
    private int count;

    public ClassifierCreator(int count) {
        this.count = count;
    }

    public WeakClassifier getClassifier(double[] weights, List<IntegralImage> positive, List<IntegralImage> negative){
        WeakClassifier best = null;
        double error = 0;

        for (int i = 0; i < count; ++i){
            HaarFeature feature = HaarFeatureFactory.getRandomFeature();
            double threshold = Rnd.nextDouble(-1, 1);
            double polarity = 1;
            if (Rnd.nextDouble() > 0.5){
                polarity = -1;
            }

            WeakClassifier classifier = new WeakClassifier(feature, threshold, polarity);
            double[] weightsCopy = new double[weights.length];
            System.arraycopy(weights, 0, weightsCopy, 0, weights.length);

            classifier.setWeights(weightsCopy);

            double currentError = classifier.getError(positive, negative);
            if (best == null || error > currentError){
                best = classifier;
                error = currentError;

                System.out.println(error);
            }
        }

        return best;
    }
}
