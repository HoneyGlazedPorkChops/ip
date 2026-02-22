package seedu.iris.command;

import java.time.LocalDateTime;

import seedu.iris.exception.IrisException;
import seedu.iris.storage.Storage;
import seedu.iris.task.Deadline;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

/**
 * Represents the deadline command which sets a task with a time limit
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDateTime by;

    /**
     * Constructs deadline command
     *
     * @param description Description of the task
     * @param by timing set by the user
     */
    public DeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        try {
            storage.save(tasks.getAll());
            return "Added the following task:\n" + deadline.toString()
                    + "\nIt is called a Deadline for a reason, better hurry up\n"
                    + "\n\n        Now you have " + tasks.size() + " tasks in the list.\n";
        } catch (IrisException e) {
            return "Added the following task:\n" + deadline.toString()
                    + "\nIt is called a Deadline for a reason, better hurry up\n"
                    + "\n\n        Now you have " + tasks.size() + " tasks in the list.\n"
                    + e.getMessage();
        }
    }
}
