package seedu.iris;

import seedu.iris.command.Command;
import seedu.iris.exception.IrisException;
import seedu.iris.exception.IrisInvalidException;
import seedu.iris.parser.Parser;
import seedu.iris.storage.Storage;
import seedu.iris.task.Task;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

/**
 * Handles loading and saving of tasks to persistent storage.
 * <p>
 * Tasks are stored in a plain text file, with one task per line.
 * Each line can be converted back into a {@link Task} using the parser.
 */
public class Iris {
    private static final String DEFAULT_FILE = "tasks.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs the Iris chatbot with the default save file.
     */
    public Iris() {
        this(DEFAULT_FILE);
    }

    /**
     * Constructs the Iris chatbot with the given save file.
     *
     * @param filePath the file used to store and load tasks
     */
    public Iris(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IrisException e) {
            ui.showError(e.getMessage());
        }
    }

    public String getResponse(String input) throws IrisInvalidException, IrisException {
        Command c = Parser.parse(input);
        return c.execute(tasks, ui, storage);
    }

    /**
     * Starts the chatbot and runs the main command loop until the user exits.
     */
    public void run() {
        ui.showWelcome();
        while (true) {
            try {
                String input = ui.readCommand();
                Command c = Parser.parse(input);
                assert c != null : "Parser must always return a command";
                c.execute(tasks, ui, storage);

                if (c.isExit()) {
                    break;
                }
            } catch (IrisException | IrisInvalidException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Iris("tasks.txt").run();
    }
}

