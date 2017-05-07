package hr.fer.zemris.zavrad.detection.neural;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class FFANN {
    private int[] layout;
    private ActivationFunction[] activationFunctions;

    private Layer[] layers;

    public FFANN(int[] layout, ActivationFunction[] activationFunctions) {
        this.layout = layout;
        this.activationFunctions = activationFunctions;

        for (int i = 0; i < layout.length; ++i){
            Layer layer = new Layer(layout[i], activationFunctions[i]);
            layers[i] = layer;
        }
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
            index += layerWeights.length;
        }
    }
}
