package seedu.iris.command;

import java.util.List;

import seedu.iris.storage.Storage;
import seedu.iris.task.Task;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

/**
 * Represents the Find command which can be called by the user
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs Find command
     *
     * @param keyword word in which the user is searching for
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {

        List<Task> matches = tasks.find(keyword);

        if (matches.isEmpty()) {
            return "Doctor Strange searched the multiverse and "
                    + "could not find a single universe where such task exists...";
        }

        StringBuilder matchString = new StringBuilder();

        for (int i = 0; i < matches.size(); i++) {
            matchString.append((matches.indexOf(matches.get(i)))).append(". ").append(matches.get(i)).append("\n");
        }

        return "Here are the matching tasks in your list:\n"
                + matchString;
    }
}
