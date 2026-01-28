package iris.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    private boolean isOverdue() {
        return !isDone && by.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        String status = isOverdue() ? " ⚠ OVERDUE" : "";
        return "[D]" + super.toString() + " (by: " + by.format(fmt) + ")" + status;
    }

    @Override
    public String toSaveString() {
        return "DEADLINE | " + (isDone ? "1" : "0") + " | " + description
                + " | " + by.format(fmt);
    }
}