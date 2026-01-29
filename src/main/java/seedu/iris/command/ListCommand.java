package seedu.iris.command;

import seedu.iris.task.TaskList;
import seedu.iris.storage.Storage;
import seedu.iris.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showLine();
        ui.showMessage("Here are the tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            ui.showMessage((i + 1) + ". " + tasks.get(i));
        }

        if (tasks.size() > 4) {
            ui.showMessage("\nAre you that busy or do you just like to procrastinate?");
        } else {
            ui.showMessage("\nLight work... Hopefully right?");
        }

        ui.showLine();
    }
}