package seedu.iris.exception;

/**
 * Represents errors specific to the Iris chatbot.
 */
public class IrisException extends Exception {
    /**
     * Creates an IrisException with the given error message.
     *
     * @param message the error message
     */
    public IrisException(String message) {
        super(message);
    }
}
