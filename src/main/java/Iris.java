import java.util.Scanner;

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

            System.out.println("____________________________________________________________\n"
                            + input + "\n"
                            + "____________________________________________________________"
            );
        }

        scanner.close();
    }
}

