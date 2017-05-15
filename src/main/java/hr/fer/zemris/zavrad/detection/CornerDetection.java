package hr.fer.zemris.zavrad.detection;

import hr.fer.zemris.zavrad.detection.neural.ActivationFunction;
import hr.fer.zemris.zavrad.detection.neural.FFANN;
import hr.fer.zemris.zavrad.detection.neural.NeuralException;
import hr.fer.zemris.zavrad.table.CornerValue;
import hr.fer.zemris.zavrad.util.Rnd;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class CornerDetection {
    private static final int INPUT_LAYER = 16;
    private static final int HIDDEN_LAYER = 13;
    private static final int OUTPUT_LAYER = 10;
    private FFANN ffann;

    public CornerDetection() {
        int[] layout = new int[]{INPUT_LAYER, HIDDEN_LAYER, OUTPUT_LAYER};
        ActivationFunction[] activationFunctions = new ActivationFunction[]{
                ActivationFunction.LINEAR,
                ActivationFunction.SIGMOID,
                ActivationFunction.SIGMOID
        };

        ffann = new FFANN(layout, activationFunctions);
    }

    public void setWeights(double[] weights){
        ffann.setWeights(weights);
    }

    public int getWeightCount(){
        return ffann.getWeightCount();
    }

    public CornerValue detect(double[] input){
        if (input.length != INPUT_LAYER){
            throw new NeuralException("Wrong number of features in input vector.");
        }

        RealVector inputVector = new ArrayRealVector(input);
        RealVector output = ffann.getOutput(inputVector);
        double[] outputArray = output.toArray();

        double maxValue = -1;
        int index = 0;
        for (int i = 0; i < outputArray.length; ++i){
            if (outputArray[i] > maxValue){
                maxValue = outputArray[i];
                index = i;
            }
        }

        return CornerValue.getCornerValue(index);
    }

    public void readWeightsFromFile(Path filePath){
        List<String> lines = null;

        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[] weights = new double[lines.size()];
        for (int i = 0; i < weights.length; ++i){
            weights[i] = Double.parseDouble(lines.get(i));
        }

        ffann.setWeights(weights);
    }

    public void writeWeightsToFile(Path filePath){
        double[] weights = ffann.getWeights();

        StringBuilder sb = new StringBuilder();
        for (double weight : weights){
            sb.append(weight);
            sb.append(String.format("%n"));
        }

        try {
            Files.write(filePath, sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FFANN getFfann(){
        return ffann;
    }

    public CornerDetection copy() {
        return new CornerDetection();
    }

    public void setRandomWeights() {
        int weightCount = ffann.getWeightCount();
        double[] weights = new double[weightCount];

        for (int i = 0; i < weightCount; ++i){
            weights[i] = Rnd.nextDouble(-1, 1);
        }

        ffann.setWeights(weights);
    }
}
