package seedu.iris.command;

import seedu.iris.storage.Storage;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

/**
 * Represents the List command which can be called by the user
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        StringBuilder lst = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            lst.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }

        String msg = "";

        if (tasks.size() > 4) {
            msg = "\nAre you that busy or do you just like to procrastinate?";
        } else {
            msg = "\nLight work... Hopefully right?";
        }

        return "Here are the tasks in your list:\n" + lst + msg;
    }
}
