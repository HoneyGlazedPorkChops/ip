public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            ui.showLine();
            ui.showMessage("Getting overzealous now are we? Unless " +
                    "you can read the future,");
            ui.showMessage("I don't think you can finish that task...");
            ui.showLine();
        } else {
            tasks.mark(index);
            storage.save(tasks.getAll());
            ui.showLine();
            ui.showMessage("Was just wondering when you were going to complete that...");
            ui.showMessage("I've marked this task as done:");
            ui.showMessage(tasks.get(index).toString());
            ui.showMessage("        Now you have " + tasks.size() + " tasks in the list.");
            ui.showMessage("\nMove on, I'm not here to give out rewards");
            ui.showLine();
        }
    }
}
