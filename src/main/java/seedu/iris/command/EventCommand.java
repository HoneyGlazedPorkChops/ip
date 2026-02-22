package seedu.iris.command;

import java.time.LocalDateTime;

import seedu.iris.exception.IrisException;
import seedu.iris.storage.Storage;
import seedu.iris.task.Event;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

/**
 * Represents the Event command which can be called by the user
 */
public class EventCommand extends Command {
    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Constructs the Event which has a start time and end time
     *
     * @param description Description of the event task
     * @param start Start time of the event task
     * @param end end time of the event task
     */
    public EventCommand(String description, LocalDateTime start, LocalDateTime end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Event event = new Event(description, start, end);
        tasks.add(event);
        try {
            storage.save(tasks.getAll());
            return "Added the following task:\n" + event.toString()
                    + "\n\n like fun... or a chore...\n"
                    + "\n\n        Now you have " + tasks.size() + " tasks in the list.\n";
        } catch (IrisException e) {
            return "Added the following task:\n" + event.toString()
                    + "\n\n like fun... or a chore...\n"
                    + "\n\n        Now you have " + tasks.size() + " tasks in the list.\n"
                    + e.getMessage();
        }
    }
}
