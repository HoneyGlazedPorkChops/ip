package seedu.iris.command;

import java.time.LocalDateTime;

import seedu.iris.storage.Storage;
import seedu.iris.task.Event;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

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
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Event event = new Event(description, start, end);
        tasks.add(event);
        storage.save(tasks.getAll());
        return "Added the following task:\n" + event.toString()
                + "\n\n like fun... or a chore...\n"
                + "\n\n        Now you have " + tasks.size() + " tasks in the list.\n";
    }
}
