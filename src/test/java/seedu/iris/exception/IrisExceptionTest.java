package seedu.iris.exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IrisExceptionTest {

    @Test
    public void constructor_messageStoredCorrectly() {
        IrisException e = new IrisException("Something went wrong");

        assertEquals("Something went wrong", e.getMessage());
    }

    @Test
    public void exception_canBeThrownAndCaught() {
        try {
            throw new IrisException("Caught");
        } catch (IrisException e) {
            assertEquals("Caught", e.getMessage());
        }
    }

    @Test
    public void exception_isInstanceOfException() {
        IrisException e = new IrisException("Test");

        assertInstanceOf(Exception.class, e);
    }
}
