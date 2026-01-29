package seedu.iris.command;

import seedu.iris.task.TaskList;
import seedu.iris.storage.Storage;
import seedu.iris.ui.Ui;

public abstract class Command {
    protected boolean isExit = false;

    public boolean isExit() {
        return isExit;
    }

    public abstract void execute(TaskList tasks, Ui ui, Storage storage);
}
