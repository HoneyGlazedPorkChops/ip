package seedu.iris.exception;

/**
 * Represents errors specific to the Iris chatbot.
 */
public class IrisInvalidException extends Exception {
    /**
     * Creates an IrisException with the given error message.
     *
     * @param message the error message
     */
    public IrisInvalidException(String message) {
        super(message);
    }
}
