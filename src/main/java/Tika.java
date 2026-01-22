import java.util.Scanner;

public class Tika {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] list = new Task[100];
        int count = 0;

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Tika");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            String[] parts = input.split(" ");
            if (parts.length == 2) {
                try {
                    int number = Integer.parseInt(parts[1]);
                    Task currTask = list[number - 1];
                    // check number smaller or equal to count
                    if (parts[0].equals("mark")) {
                        // check if mark is valid
                        currTask.toggleIsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  [" + currTask.getStatusIcon() + "] " + currTask.getDescription());
                        System.out.println("____________________________________________________________");
                        input = scanner.nextLine();
                        continue;
                    } else if (parts[0].equals("unmark")) {
                        // check if unmark is valid
                        currTask.toggleIsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  [" + currTask.getStatusIcon() + "] " + currTask.getDescription());
                        System.out.println("____________________________________________________________");
                        input = scanner.nextLine();
                        continue;
                    }
                } catch (NumberFormatException e) {
                    // ignore
                }
            }

            switch (input) {
                case "list":
                    if (count == 0) {
                        System.out.println("____________________________________________________________");
                        System.out.println("List is empty... :(");
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("____________________________________________________________");
                        for (int i = 1; i <= count; i++) {
                            Task currTask = list[i - 1];
                            System.out.println(i + ".[" + currTask.getStatusIcon() + "] " + currTask.getDescription());
                        }
                        System.out.println("____________________________________________________________");
                    }
                    input = scanner.nextLine();
                    break;
                default:
                    System.out.println("____________________________________________________________");
                    list[count] = new Task(input);
                    count++;
                    System.out.println("added: " + input);
                    System.out.println("____________________________________________________________");
                    input = scanner.nextLine();
                    break;
            }
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
