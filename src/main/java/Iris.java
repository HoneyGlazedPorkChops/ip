import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Iris {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________\n"
                + "Hello! I'm Iris\n"
                + "What can I do for you?\n"
                + "____________________________________________________________\n"
                + "Bye. Hope to see you again soon!\n"
                + "____________________________________________________________"
        );

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        while (true) {
            System.out.print("Your command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Bye")) {
                System.out.println("____________________________________________________________\n"
                        + "Bye. Hope to see you again soon!\n"
                        + "____________________________________________________________"
                );
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". [" + list.get(i).getStatusIcon() + "] "
                            + list.get(i).getDescription());
                }
                System.out.println("____________________________________________________________");
                break;
            }

            if (input.toLowerCase().startsWith("mark")) {
                String[] parts = input.split("\\s+");
                if (parts.length != 2) {
                    System.out.println("Invalid command");
                    continue;
                }
                try {
                    int index = Integer.parseInt(parts[1]) - 1;
                    if (index < 0 || index >= list.size()) {
                        System.out.println("Invalid index");
                    } else {
                        list.get(index).mark();
                        System.out.println("____________________________________________________________");
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("[" + list.get(index).getStatusIcon() + "] "
                                + list.get(index).getDescription());
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Use: mark <number>");
                }
                continue;
            }

            if (input.toLowerCase().startsWith("unmark")) {
                String[] parts = input.split("\\s+");
                if (parts.length != 2) {
                    System.out.println("Invalid command");
                    continue;
                }
                try {
                    int index = Integer.parseInt(parts[1]) - 1;
                    if (index < 0 || index >= list.size()) {
                        System.out.println("Invalid index");
                    } else {
                        list.get(index).unmark();
                        System.out.println("____________________________________________________________");
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("[" + list.get(index).getStatusIcon() + "] "
                                + list.get(index).getDescription());
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Use: unmark <number>");
                }
                continue;
            }

            list.add(new Task(input));

            System.out.println("____________________________________________________________\n"
                            + "added: " + input + "\n"
                            + "____________________________________________________________"
            );
        }

        scanner.close();
    }
}

