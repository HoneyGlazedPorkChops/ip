package seedu.iris.task;

/**
 * Represents a task that no time constraint
 */
public class ToDo extends Task {
    /**
     * Creates a ToDo
     *
     * @param description the task description
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation for display.
     *
     * @return a formatted string representing a ToDo
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string representation suitable for saving to disk.
     *
     * @return a formatted string representing a ToDo
     */
    @Override
    public String toSaveString() {
        return "TODO | " + (isDone ? "1" : "0") + " | " + description;
    }
}
