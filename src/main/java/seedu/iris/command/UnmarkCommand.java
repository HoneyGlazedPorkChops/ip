package seedu.iris.command;

import seedu.iris.task.TaskList;
import seedu.iris.storage.Storage;
import seedu.iris.ui.Ui;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            ui.showLine();
            ui.showMessage("You do realize that I can't organize " +
                    "tasks that you have not told me about right?");
            ui.showLine();
        } else {
            tasks.unmark(index);
            storage.save(tasks.getAll());
            ui.showLine();
            ui.showMessage("Looks like someone is slow... I have marked it as undone for you:");
            ui.showMessage(tasks.get(index).toString());
            ui.showMessage("        Now you have " + tasks.size() + " tasks in the list.");
            ui.showMessage("\nWhat are you waiting for? Go on then.");
            ui.showLine();
        }
    }
}
