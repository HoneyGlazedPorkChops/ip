package seedu.iris.command;

import seedu.iris.storage.Storage;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "You do realize that I can't organize "
                    + "tasks that you have not told me about right?";
        } else {
            tasks.unmark(index);
            storage.save(tasks.getAll());
            return "Looks like someone is slow... I have marked it as undone for you:\n"
                    + tasks.get(index).toString() + "\n        Now you have " + tasks.size() + " tasks in the list."
                    + "\n\nWhat are you waiting for? Go on then.";
        }
    }
}
