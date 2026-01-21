import java.util.Scanner;
import java.util.ArrayList;

public class Iris {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        System.out.println("""
                ____________________________________________________________
                Hello! I'm Iris
                What can I do for you?
                ____________________________________________________________
                """
        );

        while (true) {
            System.out.print("What would you like me to do?:\n");

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

                if (list.size() > 4) {
                    System.out.println("\nAre you that busy or do you just like to procrastinate?");
                } else {
                    System.out.println("\nLight work... Hopefully right?");
                }
                System.out.println("____________________________________________________________");
                continue;
            }

            if (input.toLowerCase().startsWith("mark")) {
                String[] parts = input.split("\\s+");
                if (parts.length != 2) {
                    System.out.println("____________________________________________________________");
                    System.out.println("I advise you to stop speaking gibberish");
                    System.out.println("____________________________________________________________");
                    continue;
                }
                try {
                    int index = Integer.parseInt(parts[1]) - 1;
                    if (index < 0 || index >= list.size()) {
                        System.out.println("____________________________________________________________");
                        System.out.println("Getting overzealous now are we? Unless " +
                                "you can read the future,");
                        System.out.println("I don't think you can finish that task...");
                        System.out.println("____________________________________________________________");
                    } else {
                        list.get(index).mark();
                        System.out.println("____________________________________________________________");
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(list.get(index).toString());
                        System.out.println("Move on, I'm not here to give out rewards");
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println("One of us just made an error and I'm sure it wasn't me");
                    System.out.println("____________________________________________________________");
                }
                continue;
            }

            if (input.toLowerCase().startsWith("unmark")) {
                String[] parts = input.split("\\s+");
                if (parts.length != 2) {
                    System.out.println("____________________________________________________________");
                    System.out.println("I advise you to stop speaking gibberish");
                    System.out.println("____________________________________________________________");
                    continue;
                }
                try {
                    int index = Integer.parseInt(parts[1]) - 1;
                    if (index < 0 || index >= list.size()) {
                        System.out.println("____________________________________________________________");
                        System.out.println("You do realize that I can't organize " +
                                "tasks that you have not told me about right?");
                        System.out.println("____________________________________________________________");
                    } else {
                        list.get(index).unmark();
                        System.out.println("____________________________________________________________");
                        System.out.println("Looks like someone is slow... I have marked it as undone for you:");
                        System.out.println(list.get(index).toString());
                        System.out.println("What are you waiting for? Go on then.");
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println("One of us just made an error and I'm sure it wasn't me");
                    System.out.println("____________________________________________________________");
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
                System.out.println("Better get it done early... or you could just be lazy");
                System.out.println("        Now you have " + list.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
            }

            if (input.toLowerCase().startsWith("deadline ")) {

                String rest = input.substring(9).trim();

                int byIndex = rest.toLowerCase().lastIndexOf(" /by ");

                if (byIndex == -1) {
                    System.out.println("____________________________________________________________");
                    System.out.println("I'm not omniscient you know? Give me more details.");
                    System.out.println("____________________________________________________________");
                } else {
                    String description = rest.substring(0, byIndex).trim();
                    String date = rest.substring(byIndex + 5).trim();

                    Deadline deadline = new Deadline(description, date);
                    list.add(deadline);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(deadline);
                    System.out.println("It is called a Deadline for a reason, better hurry up");
                    System.out.println("        Now you have " + list.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
            }

            if (input.toLowerCase().startsWith("event ")) {

                String rest = input.substring(6).trim();

                int fromIndex = rest.toLowerCase().lastIndexOf(" /from ");
                int toIndex = rest.toLowerCase().lastIndexOf(" /to ");

                if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Did you develop dementia? You missed out some crucial information!");
                    System.out.println("____________________________________________________________");
                } else {
                    String description = rest.substring(0, fromIndex).trim();
                    String start = rest.substring(fromIndex + 7, toIndex).trim();
                    String end = rest.substring(toIndex + 5).trim();


                    Event event = new Event(description, start, end);
                    list.add(event);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(event);
                    System.out.println("Sounds like fun... or a chore...");
                    System.out.println("        Now you have " + list.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }
            }
        }

        scanner.close();
    }
}

