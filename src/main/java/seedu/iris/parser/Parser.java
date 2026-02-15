package seedu.iris.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.iris.command.ByeCommand;
import seedu.iris.command.Command;
import seedu.iris.command.DeadlineCommand;
import seedu.iris.command.DeleteCommand;
import seedu.iris.command.EventCommand;
import seedu.iris.command.FindCommand;
import seedu.iris.command.HelpCommand;
import seedu.iris.command.ListCommand;
import seedu.iris.command.MarkCommand;
import seedu.iris.command.TodoCommand;
import seedu.iris.command.UnmarkCommand;
import seedu.iris.exception.IrisException;
import seedu.iris.exception.IrisInvalidException;

/**
 * Converts raw user input into executable commands.
 */
public class Parser {
    /**
     * Parses a single line of user input into a {@link Command}.
     *
     * @param input the raw user input
     * @return a command object representing the user's request
     * @throws IrisException if the input is not a valid command
     */
    @SuppressWarnings("checkstyle:LineLength")
    public static Command parse(String input) throws IrisException, IrisInvalidException {
        String[] words = input.split(" ");

        assert words.length > 0 : "Input must have at least one token";

        if (input.equalsIgnoreCase("bye")) {
            return new ByeCommand();
        }

        if (input.equalsIgnoreCase("list")) {
            return new ListCommand();
        }

        if (input.toLowerCase().startsWith("mark")) {
            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                throw new IrisException("I advise you to stop speaking gibberish");
            }

            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new MarkCommand(index);
            } catch (NumberFormatException e) {
                throw new IrisException("One of us just made an error and I'm sure it wasn't me");
            }
        }

        if (input.toLowerCase().startsWith("unmark")) {
            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                throw new IrisException("I advise you to stop speaking gibberish");
            }

            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new UnmarkCommand(index);
            } catch (NumberFormatException e) {
                throw new IrisException("One of us just made an error and I'm sure it wasn't me");
            }
        }

        if (input.toLowerCase().startsWith("delete")) {
            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                throw new IrisException("I advise you to stop speaking gibberish");
            }

            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new DeleteCommand(index);
            } catch (NumberFormatException e) {
                throw new IrisException("One of us just made an error and I'm sure it wasn't me");
            }
        }

        if (input.toLowerCase().startsWith("todo ")) {
            String description = input.substring(5).trim();
            return new TodoCommand(description);
        }

        if (input.toLowerCase().startsWith("deadline ")) {
            String rest = input.substring(9).trim();

            int byIndex = rest.toLowerCase().lastIndexOf(" /by ");

            if (byIndex == -1) {
                throw new IrisException("I'm not omniscient you know? Give me more details.");
            }

            try {
                String description = rest.substring(0, byIndex).trim();
                String dateString = rest.substring(byIndex + 5).trim();
                LocalDateTime date = parseDateTime(dateString);
                return new DeadlineCommand(description, date);
            } catch (DateTimeParseException e) {
                throw new IrisException("Losing a few screws? That date doesn't exist in this universe.\n"
                        + "Use format YYYY-MM-DD or YYYY-MM-DD HH:MM");
            }
        }

        if (input.toLowerCase().startsWith("event ")) {
            String rest = input.substring(6).trim();

            int fromIndex = rest.toLowerCase().lastIndexOf(" /from ");
            int toIndex = rest.toLowerCase().lastIndexOf(" /to ");

            if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                throw new IrisException("Did you develop dementia? You missed out some crucial information!");
            }

            try {
                String description = rest.substring(0, fromIndex).trim();
                String start = rest.substring(fromIndex + 7, toIndex).trim();
                String end = rest.substring(toIndex + 5).trim();
                LocalDateTime startDate = parseDateTime(start);
                LocalDateTime endDate = parseDateTime(end);

                if (endDate.isBefore(startDate)) {
                    throw new IrisException("Did not know time travelled backwards for you."
                            + "The end date is before the start date!");
                }

                return new EventCommand(description, startDate, endDate);
            } catch (DateTimeParseException e) {
                throw new IrisException("Please abide by the standard format or else...\n"
                        + "Invalid date format. Use YYYY-MM-DD or YYYY-MM-DD HH:MM");
            }
        }

        if (input.startsWith("find ")) {
            String keyword = input.substring(5).trim();
            if (keyword.isEmpty()) {
                throw new IrisException("Find what?");
            }

            return new FindCommand(keyword);
        }

        if (input.startsWith("help")) {
            return new HelpCommand();
        }

        throw new IrisInvalidException("Has your age finally caught up with you?\nI need a valid command!");
    }

    private static LocalDateTime parseDateTime(String input) {
        DateTimeFormatter dateTimeFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDateTime.parse(input, dateTimeFmt);
        } catch (DateTimeParseException e) {
            // input does not include timing, try parsing with just date next
        }

        try {
            LocalDate date = LocalDate.parse(input, dateFmt);
            return date.atStartOfDay(); // set time to start of day if none is specified
        } catch (DateTimeParseException ex) {
            throw new DateTimeParseException("Invalid date/time format", input, 0);
        }
    }
}

