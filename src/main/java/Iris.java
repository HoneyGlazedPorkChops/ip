import java.util.Scanner;
import java.util.ArrayList;

public class Iris {
    public static void main(String[] args) {
        System.out.println("""
                ____________________________________________________________
                Hello! I'm Iris
                What can I do for you?
                ____________________________________________________________
                Bye. Hope to see you again soon!
                ____________________________________________________________"""
        );

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        while (true) {
            System.out.print("Your command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Bye")) {
                System.out.println("""
                        ____________________________________________________________
                        Bye. Hope to see you again soon!
                        ____________________________________________________________"""
                );
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i).toString());
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
                        System.out.println(list.get(index).toString());
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
                        System.out.println(list.get(index).toString());
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Use: unmark <number>");
                }
                continue;
            }

            if (input.toLowerCase().startsWith("todo ")) {

                String description = input.substring(5).trim();

                ToDo todo = new ToDo(description);
                list.add(todo);
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(todo);
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
            }

            if (input.toLowerCase().startsWith("deadline ")) {

                String rest = input.substring(9).trim();

                int byIndex = rest.toLowerCase().lastIndexOf(" /by ");

                if (byIndex == -1) {
                    System.out.println("Invalid format. Use: Deadline <description> /by <date>");
                } else {
                    String description = rest.substring(0, byIndex).trim();
                    String date = rest.substring(byIndex + 5).trim();

                    if (description.isEmpty() || date.isEmpty()) {
                        System.out.println("Invalid format. Use: Deadline <description> /by <date>");
                    } else {
                        Deadline deadline = new Deadline(description, date);
                        list.add(deadline);
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println(deadline);
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    }
                }
            }

            if (input.toLowerCase().startsWith("event ")) {

                String rest = input.substring(6).trim();

                int fromIndex = rest.toLowerCase().lastIndexOf(" /from ");
                int toIndex = rest.toLowerCase().lastIndexOf(" /to ");

                if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                    System.out.println("Invalid format. Use: event <description> /from <start> /to <end>");
                } else {
                    String description = rest.substring(0, fromIndex).trim();
                    String start = rest.substring(fromIndex + 7, toIndex).trim();
                    String end = rest.substring(toIndex + 5).trim();

                    if (description.isEmpty() || start.isEmpty() || end.isEmpty()) {
                        System.out.println("Invalid format. Use: event <description> /from <start> /to <end>");
                    } else {
                        Event event = new Event(description, start, end);
                        list.add(event);
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println(event);
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    }
                }
            }
        }

        scanner.close();
    }
}

