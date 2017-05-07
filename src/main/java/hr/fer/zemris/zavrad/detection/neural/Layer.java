package hr.fer.zemris.zavrad.detection.neural;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class Layer {
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

    public void setInput(RealVector input){
        if (input.getDimension() != values.getDimension()){
            throw new NeuralException("The size of the input vector is different than the size of the layer.");
        }

        values = input;
    }

    public void setWeights(double[] weights){

    }
}
