package iris.command;

import iris.task.TaskList;
import iris.storage.Storage;
import iris.task.Event;
import iris.ui.Ui;

import java.time.LocalDateTime;

public class EventCommand extends Command {
    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public EventCommand(String description, LocalDateTime start, LocalDateTime end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Event event = new Event(description, start, end);
        tasks.add(event);
        storage.save(tasks.getAll());
        ui.showLine();
        ui.showMessage("Added the following task:");
        ui.showMessage(event.toString());
        ui.showMessage("Sounds like fun... or a chore...");
        ui.showMessage("        Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }
}
