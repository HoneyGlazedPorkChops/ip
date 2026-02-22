package seedu.iris.exception;

/**
 * Represents errors specific to the Iris chatbot regarding invalid commands.
 */
public class IrisInvalidException extends Exception {
    /**
     * Creates an IrisInvalidException with the given error message.
     *
     * @param message the error message
     */
    public IrisInvalidException(String message) {
        super(message);
    }
}
