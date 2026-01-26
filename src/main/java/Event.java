import java.time.LocalDate;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    private boolean isOver() {
        return to.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        String status = isOver() ? " OVER :(" : "";
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")"
                + status;
    }

    @Override
    public String toSaveString() {
        return "EVENT | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }
}
