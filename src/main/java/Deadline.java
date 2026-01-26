import java.time.LocalDate;

public class Deadline extends Task {
    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    private boolean isOverdue() {
        return !isDone && by.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        String status = isOverdue() ? " ⚠ OVERDUE" : "";
        return "[D]" + super.toString() + " (by: " + by + ")" + status;
    }

    @Override
    public String toSaveString() {
        return "DEADLINE | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}