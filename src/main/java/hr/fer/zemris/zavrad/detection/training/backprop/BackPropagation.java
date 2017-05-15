package hr.fer.zemris.zavrad.detection.training.backprop;

import hr.fer.zemris.zavrad.detection.neural.FFANN;
import hr.fer.zemris.zavrad.detection.neural.Layer;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class BackPropagation {
    private FFANN ffann;
    private double learningRate;
    private Layer[] layers;

    public BackPropagation(FFANN ffann, double learningRate) {
        this.ffann = ffann;
        this.learningRate = learningRate;

        layers = ffann.getLayers();
    }

    public void propagate(RealVector input, int expectedOutput){
        RealVector output = ffann.getOutput(input);

        int n = layers.length;
        Layer outputLayer = layers[n - 1];
        double[] error = new double[outputLayer.getSize()];

        for (int i = 0; i < error.length; ++i){
            if (expectedOutput == i){
                error[i] = output.getEntry(i) - 1;
            } else {
                error[i] = output.getEntry(i);
            }
        }

        RealMatrix weights = outputLayer.getWeightMatrix();
        for (int i = 0; i < outputLayer.getSize(); ++i){
            double delta = output.getEntry(i) * (1 - output.getEntry(i)) * error[i];

            RealVector neuronWeights = weights.getColumnVector(i);

            RealVector inputs = layers[n - 2].getValues();
            for (int j = 0; j < neuronWeights.getDimension(); ++j){
                double weight = neuronWeights.getEntry(j);
                double inputValue = (j == 0 ? 1 : inputs.getEntry(j - 1));
                weight -= learningRate * delta * inputValue;
                neuronWeights.setEntry(j, weight);
            }

            weights.setColumnVector(i, neuronWeights);
        }
    }
}
