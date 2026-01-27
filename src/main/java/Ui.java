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

    public void showBye() {
        System.out.println("""
                ____________________________________________________________
                 Bye. Hope to see you again soon!
                ____________________________________________________________
                """);
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

}
