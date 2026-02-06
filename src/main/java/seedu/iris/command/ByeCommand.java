package seedu.iris.command;

import seedu.iris.storage.Storage;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

public class ByeCommand extends Command {
    public ByeCommand() {
        isExit = true;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return """
                 Bye. Hope to see you again soon!
                """;
    }
}
