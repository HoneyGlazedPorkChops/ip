package seedu.iris.command;

import seedu.iris.task.TaskList;
import seedu.iris.storage.Storage;
import seedu.iris.task.ToDo;
import seedu.iris.ui.Ui;

public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ToDo todo = new ToDo(description);
        tasks.add(todo);
        storage.save(tasks.getAll());
        ui.showLine();
        ui.showMessage("Added the following task:");
        ui.showMessage(todo.toString());
        ui.showMessage("Better get it done early... or you could just be lazy");
        ui.showMessage("        Now you have " + tasks.size() + " tasks in the list.");
        ui.showLine();
    }
}
