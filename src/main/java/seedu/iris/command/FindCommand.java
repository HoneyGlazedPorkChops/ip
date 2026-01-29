package seedu.iris.command;

import seedu.iris.storage.Storage;
import seedu.iris.task.Task;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;
import java.util.ArrayList;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matches = tasks.find(keyword);

        if (matches.isEmpty()) {
            ui.showMessage("Doctor Strange searched the multiverse and " +
                    "could not find a single universe where such task exists...");
            return;
        }

        ui.showMessage("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            ui.showMessage((i + 1) + ". " + matches.get(i));
        }
    }
}
