package seedu.iris.command;

import seedu.iris.storage.Storage;
import seedu.iris.task.TaskList;
import seedu.iris.ui.Ui;

/**
 * Represents the Help command which can be called by the user
 */
public class HelpCommand extends Command {

    /**
     * Constructs Help command with the default constructor
     */
    public HelpCommand() { }

    @SuppressWarnings("checkstyle:Regexp")
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {

        return """
                LIST - Lists down all the task recorded thus far
                [list]
                
                TODO - Adds a task without any time restriction
                [todo <task>]
                
                DEADLINE - Adds a task with a deadline to meet
                [deadline <task> /by <YYYY-MM-DD> (HH:MM)]
                
                EVENT - Adds a task with specific time frame
                [event <task> /from <YYYY-MM-DD> (HH:MM) /to <YYYY-MM-DD> (HH:MM)]
                
                MARK - mark a task as done
                [mark <index>]
                
                UNMARK - unmark a task as incomplete
                [unmark <index>]
                
                DELETE - remove a task from the list
                [delete <index>]
                
                FIND - Lists every task with a matching keyword in the description
                [find <keyword>]
                
                BYE - closes the application
                [bye]
                """;
    }
}
