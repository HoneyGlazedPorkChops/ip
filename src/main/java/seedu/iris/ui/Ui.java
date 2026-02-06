package seedu.iris.ui;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.print("What would you like me to do?:\n> ");
        return scanner.nextLine().trim();
    }

    public void showWelcome() {
        System.out.println("""
                ____________________________________________________________
                 Hello! I'm Iris
                 What can I do for you?
                ____________________________________________________________
                """);
    }

    public String getWelcome() {
        return """
                ____________________________________________________________
                 Hello! I'm Iris
                 What can I do for you?
                ____________________________________________________________
                """;
    }

    public String getBye() {
        return """
                ____________________________________________________________
                 Bye. Hope to see you again soon!
                ____________________________________________________________
                """;
    }

    public String showError(String message) {
        return "____________________________________________________________\n"
                + message + "\n____________________________________________________________";
    }

    public String showLine() {
        return "____________________________________________________________";
    }

}
