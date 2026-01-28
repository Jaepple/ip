package tika;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Tika");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showMessage(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void markTask(Task currTask) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + currTask.toString());
        showLine();
    }

    public void unmarkTask(Task currTask) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + currTask.toString());
        showLine();
    }

    public void deleteTask(Task currTask, int count) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + currTask.toString());
        System.out.println("Now you have " + count + " tasks in the list.");
        showLine();
    }

    public void addTask(Task newTask, int count) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newTask.toString());
        System.out.println("Now you have " + count + " tasks in the list.");
        showLine();
    }

    public void showBye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }
}
