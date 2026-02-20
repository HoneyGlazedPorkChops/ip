package seedu.iris.command;

import seedu.iris.exception.IrisException;
import seedu.iris.storage.Storage;
import seedu.iris.task.TaskList;
import seedu.iris.task.ToDo;
import seedu.iris.ui.Ui;

public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ToDo todo = new ToDo(description);
        tasks.add(todo);
        try {
            storage.save(tasks.getAll());
            return "Added the following task:\n" + todo.toString()
                    + "\nBetter get it done early... or you could just be lazy"
                    + "\n\n        Now you have " + tasks.size() + " tasks in the list.\n";
        } catch (IrisException e) {
            return "Added the following task:\n" + todo.toString()
                    + "\nBetter get it done early... or you could just be lazy"
                    + "\n\n        Now you have " + tasks.size() + " tasks in the list.\n"
                    + e.getMessage();
        }
    }
}
