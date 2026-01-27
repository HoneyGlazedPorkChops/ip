import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDateTime by;

    public DeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        storage.save(tasks.getAll());
        ui.showLine();
        ui.showMessage("Added the following task:");
        ui.showMessage(deadline.toString());
        ui.showMessage("It is called a Deadline for a reason, better hurry up");
        ui.showMessage("        Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }
}
