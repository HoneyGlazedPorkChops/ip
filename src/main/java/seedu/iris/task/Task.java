package seedu.iris.task;

/**
 * Represents a task that can be tracked by the chatbot.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the task description.
     *
     * @return the description of this task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation for display.
     *
     * @return a formatted string representing this task
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns a string representation suitable for saving to disk.
     *
     * @return a formatted string representing this task
     */
    public abstract String toSaveString();
}
