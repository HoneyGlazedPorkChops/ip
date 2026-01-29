package seedu.iris.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    private boolean isOver() {
        return to.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String status = isOver() ? " OVER :(" : "";
        return "[E]" + super.toString() +
                " (from: " + from.format(fmt) + " to: " + to.format(fmt) + ")" + status;
    }

    @Override
    public String toSaveString() {
        return "EVENT | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(fmt) + " | " + to.format(fmt);
    }
}
