package seedu.iris.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event that occurs within a specific time period.
 */
public class Event extends Task {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates an event with a start and end time.
     *
     * @param description the event description
     * @param from the start date and time
     * @param to the end date and time
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns whether the event has already ended.
     *
     * @return true if the current time is after the end time
     */
    private boolean isOver() {
        return to.isBefore(LocalDateTime.now());
    }

    /**
     * Returns a string representation for display.
     *
     * @return a formatted string representing an Event
     */
    @Override
    public String toString() {
        String status = isOver() ? " OVER :(" : "";
        return "[E]" + super.toString() + " (from: "
                + from.format(FORMAT) + " to: " + to.format(FORMAT) + ")" + status;
    }

    /**
     * Returns a string representation suitable for saving to disk.
     *
     * @return a formatted string representing an Event
     */
    @Override
    public String toSaveString() {
        return "EVENT | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(FORMAT) + " | " + to.format(FORMAT);
    }
}
