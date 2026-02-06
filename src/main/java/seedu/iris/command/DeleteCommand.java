package seedu.iris.command;

import seedu.iris.storage.Storage;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return """
                    It appears that you are lacking a frontal lobe and have
                    asked me to remove something that has not yet existed...
                    """;
        } else {
            String msg = tasks.get(index).toString();
            tasks.remove(index);
            storage.save(tasks.getAll());
            return "Here I was beginning to think you have forgotten about this...\n"
                    + "I have removed the following task:\n"
                    + msg + "\n        Now you have " + tasks.size() + " tasks in the list."
                    + "\nMore free time for you then... Enjoy.";
        }
    }
}
