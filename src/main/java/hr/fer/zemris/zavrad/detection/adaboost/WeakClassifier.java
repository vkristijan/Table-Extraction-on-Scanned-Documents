package hr.fer.zemris.zavrad.detection.adaboost;

import hr.fer.zemris.zavrad.detection.features.feature.haar.HaarFeature;
import hr.fer.zemris.zavrad.util.img.GrayScaleImage;
import hr.fer.zemris.zavrad.util.img.IntegralImage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class WeakClassifier {
    private HaarFeature feature;
    private double threshold;
    private double polarity;
    private double alpha;

    public double getAlpha() {
        return alpha;
    }

    private double[] weights;

    public WeakClassifier(HaarFeature feature, double threshold, double polarity) {
        this.feature = feature;
        this.threshold = threshold;
        this.polarity = polarity;
    }

    public HaarFeature getFeature() {
        return feature;
    }

    public void setFeature(HaarFeature feature) {
        this.feature = feature;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getPolarity() {
        return polarity;
    }

    public void setPolarity(double polarity) {
        this.polarity = polarity;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public int classify(IntegralImage img, int x, int y, int w, int h){
        double value = feature.getFeature(img, x, y, w, h);
        if (polarity * value < polarity * threshold){
            return 1;
        }
        return 0;
    }

    public double getError(List<IntegralImage> positive, List<IntegralImage> negative){
        Set<Integer> errors = new HashSet<>();

        normalizeWeights();
        double error = 0;

        int index = 0;
        for (IntegralImage img : positive){
            if (classify(img, 0, 0, img.getWidth(), img.getHeight()) != 1){
                error += weights[index];
                errors.add(index);
            }

            index++;
        }

        for (IntegralImage img : negative){
            if (classify(img, 0, 0, img.getWidth(), img.getHeight()) != 0){
                error += weights[index];
                errors.add(index);
            }

            index++;
        }

        updateWeights(errors, error);
        return error;
    }

    private void updateWeights(Set<Integer> errors, double error) {
        double beta = error / (1 - error);

        for (int i = 0; i < weights.length; ++i){
            if (errors.contains(i)){
                weights[i] *= beta;
            }
        }

        alpha = Math.log(1.0 / beta);
    }

    private void normalizeWeights(){
        double sum = 0;
        for (double weight : weights){
            sum += weight;
        }

        for (int i = 0; i < weights.length; ++i){
            weights[i] /= sum;
        }
    }
}
