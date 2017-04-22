package hr.fer.zemris.zavrad.util.img.filters;

/**
 * Exception that can occur in an image filter.
 *
 * @author Kristijan Vulinović
 * @version 1.0.0
 */
public class ImageFilterException extends RuntimeException {
    /**
     * Creates a blank {@link ImageFilterException}.
     */
    public ImageFilterException() {
    }

    /**
     * Creates a new {@link ImageFilterException} with the given message.
     *
     * @param message the message of the exception.
     */
    public ImageFilterException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link ImageFilterException} with the given message and cause.
     *
     * @param message the message of the exception.
     * @param cause the cause of the exception.
     */
    public ImageFilterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new {@link ImageFilterException} with the given cause.
     *
     * @param cause the cause of the exception.
     */
    public ImageFilterException(Throwable cause) {
        super(cause);
    }
}
