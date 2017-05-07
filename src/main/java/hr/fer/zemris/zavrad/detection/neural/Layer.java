package hr.fer.zemris.zavrad.detection.neural;

import org.apache.commons.math3.linear.*;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Layer {
    private static final ArrayRealVector BIAS = new ArrayRealVector(new double[]{1});

    private Layer previous;
    private Layer next;

    private ActivationFunction activationFunction;
    private RealVector values;
    private RealMatrix weights;

    public Layer(int size, ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;

        values = new ArrayRealVector(size);
    }

    public int getSize(){
        return values.getDimension();
    }

    public void setNext(Layer layer){
        this.next = layer;
        layer.previous = this;
    }

    public void setPrevious(Layer layer){
        this.previous = layer;
        layer.next = this;
    }

    public void setInput(RealVector input){
        if (previous != null){
            throw new NeuralException("Unable to set input for a layer other than the input layer!");
        }

        if (input.getDimension() != values.getDimension()){
            throw new NeuralException("The size of the input vector is different than the size of the layer.");
        }

        values = input;
    }

    public void setWeights(double[] weights){
        int rows = previous.getSize() + 1;
        int cols = getSize();

        this.weights = MatrixUtils.createRealMatrix(rows, cols);

        int index = 0;
        for (int i = 0; i < rows; ++i){
            double[] rowWeights = new double[cols];
            System.arraycopy(weights, index, rowWeights, 0, cols);
            index += cols;
            this.weights.setRow(i, rowWeights);
        }
    }

    public double[] getWeights(){
        int rows = this.weights.getRowDimension();
        int cols = this.weights.getColumnDimension();

        double[] weights = new double[rows * cols];
        int index = 0;
        for (int i = 0; i < rows; ++i){
            double[] rowWeights = this.weights.getRow(i);
            System.arraycopy(rowWeights, 0, weights, index, cols);
            index += cols;
        }

        return weights;
    }

    public RealVector getValues(){
        return values;
    }

    public void calculateValues(){
        RealVector input = new ArrayRealVector(BIAS, previous.getValues());
        values = weights.preMultiply(input);
    }

    public int getWeightCount() {
        int rows = previous.getSize() + 1;
        int cols = getSize();

        return rows * cols;
    }
}
