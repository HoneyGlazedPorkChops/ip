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
        List<String> list = new ArrayList<>();

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
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
                System.out.println("____________________________________________________________");
                break;
            }

            list.add(input);

            System.out.println("____________________________________________________________\n"
                            + "added: " + input + "\n"
                            + "____________________________________________________________"
            );
        }

        scanner.close();
    }
}

