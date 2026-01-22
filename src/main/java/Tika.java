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
            String firstWord = parts[0];
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

            switch (firstWord) {
                case "list":
                    if (count == 0) {
                        System.out.println("____________________________________________________________");
                        System.out.println("List is empty... :(");
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("____________________________________________________________");
                        for (int i = 1; i <= count; i++) {
                            Task currTask = list[i - 1];
                            System.out.println(i + "." + currTask.toString());
                        }
                        System.out.println("____________________________________________________________");
                    }
                    input = scanner.nextLine();
                    break;
                case "todo", "deadline", "event":
                    Task newTask;
                    String description;
                    switch (firstWord) {
                        case "todo":
                            description = input.substring("todo ".length());
                            newTask = new ToDo(description);
                            list[count] = newTask;
                            break;
                        case "deadline":
                            String[] split = input.split(" /by ");
                            description = split[0].substring("deadline ".length());
                            newTask = new Deadline(description, split[1]);
                            list[count] = newTask;
                            break;
                        case "event":
                            String[] split1 = input.split(" /from ");
                            description = split1[0].substring("event ".length());
                            String[] split2 = split1[1].split(" /to ");
                            newTask = new Event(description, split2[0], split2[1]);
                            list[count] = newTask;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + firstWord);
                    }
                    count++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask.toString());
                    System.out.println("Now you have " + count + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    input = scanner.nextLine();
                    break;
                default:
                    System.out.println("____________________________________________________________");
                    System.out.println("Not a valid task type! Try again.");
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
