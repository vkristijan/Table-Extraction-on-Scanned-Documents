package hr.fer.zemris.zavrad.detection.neural;

import org.apache.commons.math3.linear.RealVector;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class FFANN {
    private Layer[] layers;

    public FFANN(int[] layout, ActivationFunction[] activationFunctions) {
        layers = new Layer[layout.length];
        for (int i = 0; i < layout.length; ++i){
            Layer layer = new Layer(layout[i], activationFunctions[i]);
            layers[i] = layer;

            if (i > 0){
                layer.setPrevious(layers[i - 1]);
            }
        }
    }

    public RealVector getOutput(RealVector input){
        layers[0].setInput(input);

        for (int i = 1; i < layers.length; ++i){
            layers[i].calculateValues();
        }

        return layers[layers.length - 1].getValues();
    }

    public int getWeightCount(){
        int count = 0;

        for (Layer layer : layers){
            count += layer.getWeightCount();
        }

        return count;
    }

    public double[] getWeights(){
        double[] weights = new double[getWeightCount()];

        int index = 0;
        for (Layer layer : layers){
            double[] layerWeights = layer.getWeights();
            System.arraycopy(layerWeights, 0, weights, index, layerWeights.length);
            index += layerWeights.length;
        }

        return weights;
    }

    public void setWeights(double[] weights){
        int index = 0;
        for (Layer layer : layers){
            double[] layerWeights = new double[layer.getWeightCount()];
            System.arraycopy(weights, index, layerWeights, 0, layerWeights.length);
            layer.setWeights(layerWeights);
            index += layerWeights.length;
        }
    }

    public Layer[] getLayers(){
        return layers;
    }
}
