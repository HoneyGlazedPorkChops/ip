package seedu.iris.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that must be completed by a specific date and time.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Creates a deadline task with the given description and due date.
     *
     * @param description the task description
     * @param by the date and time by which the task must be completed
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns whether this deadline has passed.
     *
     * @return true if the current time is after the deadline
     */
    private boolean isOverdue() {
        return !isDone && by.isBefore(LocalDateTime.now());
    }

    /**
     * Returns a string representation for display.
     *
     * @return a formatted string representing a Deadline
     */
    @Override
    public String toString() {
        String status = isOverdue() ? " ⚠ OVERDUE" : "";
        return "[D]" + super.toString() + " (by: " + by.format(fmt) + ")" + status;
    }

    /**
     * Returns a string representation suitable for saving to disk.
     *
     * @return a formatted string representing a Deadline
     */
    @Override
    public String toSaveString() {
        return "DEADLINE | " + (isDone ? "1" : "0") + " | " + description
                + " | " + by.format(fmt);
    }
}