public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            ui.showLine();
            ui.showMessage("It appears that you are lacking a frontal lobe and have");
            ui.showMessage("asked me to remove something that has not yet existed...");
            ui.showLine();
        } else {
            ui.showLine();
            ui.showMessage("Here I was beginning to think you have forgotten about this...");
            ui.showMessage("I have removed the following task:");
            ui.showMessage(tasks.get(index).toString());
            tasks.remove(index);
            storage.save(tasks.getAll());
            ui.showMessage("        Now you have " + tasks.size() + " tasks in the list.");
            ui.showMessage("\nMore free time for you then... Enjoy.");
            ui.showLine();
        }
    }
}
