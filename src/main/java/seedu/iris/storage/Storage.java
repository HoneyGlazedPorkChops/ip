package seedu.iris.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import seedu.iris.exception.IrisException;
import seedu.iris.task.Deadline;
import seedu.iris.task.Event;
import seedu.iris.task.Task;
import seedu.iris.task.ToDo;

/**
 * Handles loading and saving of tasks to persistent storage.
 * <p>
 * Tasks are stored in a plain text file, with one task per line.
 * Each line can be converted back into a {@link Task} using the parser.
 */
public class Storage {
    private static final DateTimeFormatter SAVE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String filePath;

    /**
     * Creates a storage object that reads from and writes to the given file.
     *
     * @param fileName the path to the file used for saving tasks
     */
    public Storage(String fileName) {
        this.filePath = fileName;
    }

    /**
     * Loads all saved tasks from the storage file.
     * <p>
     * If the file does not exist, an empty task list is returned.
     *
     * @return a list of tasks loaded from disk
     * @throws IrisException if the file exists but cannot be read or parsed
     */
    public ArrayList<Task> load() throws IrisException {
        File file = new File(this.filePath);
        ArrayList<Task> list = new ArrayList<Task>();

        if (!file.exists()) {
            return list;
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Task t = parseTask(line);
                if (t != null) {
                    list.add(t);
                }
            }
        } catch (IOException e) {
            throw new IrisException("It appears there was an error when loading saved tasks...");
        }

        return list;
    }

    /**
     * Saves all tasks to the storage file.
     * <p>
     * Existing file contents will be overwritten.
     *
     * @param tasks the list of tasks to save
     */
    public void save(ArrayList<Task> tasks) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(this.filePath))) {
            for (Task t : tasks) {
                pw.println(t.toSaveString());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    private static Task parseTask(String line) {
        try {
            String[] parts = line.split("\\|");

            String type = parts[0].trim();
            boolean done = parts[1].trim().equals("1");

            Task t = null;

            switch (type) {
            case "TODO":
                t = new ToDo(parts[2].trim());
                break;
            case "DEADLINE":
                LocalDateTime by = LocalDateTime.parse(parts[3].trim(), SAVE_FMT);
                t = new Deadline(parts[2].trim(), by);
                break;
            case "EVENT":
                LocalDateTime from = LocalDateTime.parse(parts[3].trim(), SAVE_FMT);
                LocalDateTime to = LocalDateTime.parse(parts[4].trim(), SAVE_FMT);
                t = new Event(parts[2].trim(), from, to);
                break;
            default:
                break;
            }

            if (done && t != null) {
                t.mark();
            }

            return t;

        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            System.out.println("⚠ Warning: Skipped corrupted save line:");
            System.out.println("    " + line);
            return null;
        }
    }
}
