package seedu.iris.command;

import seedu.iris.storage.Storage;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

/**
 * Represents a command entered by the user.
 * <p>
 * Each command performs a specific action when executed,
 * such as adding a task, deleting one, or listing tasks.
 */
public abstract class Command {
    protected boolean isExit = false;

    /**
     * Indicates whether this command should exit the program.
     *
     * @return true if the program should terminate, false otherwise
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Executes the command.
     *
     * @param tasks   the task list to operate on
     * @param ui      the user interface
     * @param storage the storage used for saving tasks
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);
}
