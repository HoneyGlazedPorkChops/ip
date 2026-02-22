package seedu.iris.ui;

import java.util.Scanner;

/**
 * UI that manages all inputs and outputs, displaying all text and errors accordingly
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Waits to receive command from user
     */
    public String readCommand() {
        System.out.print("What would you like me to do?:\n> ");
        return scanner.nextLine().trim();
    }

    /**
     * Displays welcome message on CLI
     */
    public void showWelcome() {
        System.out.println("""
                ____________________________________________________________
                 Hello! I'm Iris
                 What can I do for you?
                ____________________________________________________________
                """);
    }

    /**
     * Displays bye message on CLI
     */
    public void getBye() {
        System.out.println("""
                ____________________________________________________________
                 Bye. Hope to see you again soon!
                ____________________________________________________________
                """);
    }

    /**
     * Displays error message on CLI
     */
    public String showError(String message) {
        return "____________________________________________________________\n"
                + message + "\n____________________________________________________________";
    }

    /**
     * Displays a line on CLI
     */
    public String showLine() {
        return "____________________________________________________________";
    }

}
