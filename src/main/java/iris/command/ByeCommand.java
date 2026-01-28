package iris.command;

import iris.task.TaskList;
import iris.storage.Storage;
import iris.ui.Ui;

public class ByeCommand extends Command {
    public ByeCommand() {
        isExit = true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }
}
