import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Storage {
    private static final DateTimeFormatter SAVE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String filePath;

    public Storage(String fileName) {
        this.filePath = fileName;
    }

    public ArrayList<Task> load() {
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
            System.out.println("It appears there was an error when loading saved tasks...");
        }

        return list;
    }

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
                case "TODO" -> t = new ToDo(parts[2].trim());
                case "DEADLINE" -> {
                    LocalDateTime by = LocalDateTime.parse(parts[3].trim(), SAVE_FMT);
                    t = new Deadline(parts[2].trim(), by);
                }
                case "EVENT" -> {
                    LocalDateTime from = LocalDateTime.parse(parts[3].trim(), SAVE_FMT);
                    LocalDateTime to = LocalDateTime.parse(parts[4].trim(), SAVE_FMT);
                    t = new Event(parts[2].trim(), from, to);
                }
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
