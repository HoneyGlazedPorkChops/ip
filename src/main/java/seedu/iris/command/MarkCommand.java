package seedu.iris.command;

import seedu.iris.exception.IrisException;
import seedu.iris.storage.Storage;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "Getting overzealous now are we? Unless "
                    + "you can read the future,\n" + "I don't think you can finish that task...";
        } else {
            tasks.mark(index);
            try {
                storage.save(tasks.getAll());
                return "Was just wondering when you were going to complete that..."
                        + "\nI've marked this task as done:\n" + tasks.get(index).toString()
                        + "\n        Now you have " + tasks.size() + " tasks in the list."
                        + "\n\nMove on, I'm not here to give out rewards";
            } catch (IrisException e) {
                return "Was just wondering when you were going to complete that..."
                        + "\nI've marked this task as done:\n" + tasks.get(index).toString()
                        + "\n        Now you have " + tasks.size() + " tasks in the list."
                        + "\n\nMove on, I'm not here to give out rewards\n"
                        + e.getMessage();
            }
        }
    }
}
