package hr.fer.zemris.zavrad.detection.neural;

/**
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class NeuralException extends RuntimeException {
    public NeuralException() {
    }

    public NeuralException(String message) {
        super(message);
    }

    public NeuralException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeuralException(Throwable cause) {
        super(cause);
    }
}
