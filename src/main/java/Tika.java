import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

public class Tika {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage();
        ArrayList<Task> list = storage.load();
        int count = list.size();

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Tika");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            try {
                String[] parts = input.split(" ");
                String firstWord = parts[0];
                if (parts.length == 2) {
                    try {
                        int number = Integer.parseInt(parts[1]);
                        Task currTask = list.get(number - 1);
                        if (number > count) {
                            throw new TikaException("Not a valid task!");
                        }
                        if (parts[0].equals("mark")) {
                            if (currTask.isDone) {
                                throw new TikaException("Task is already marked.");
                            }
                            currTask.toggleIsDone();
                            storage.save(list);
                            System.out.println("____________________________________________________________");
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println("  " + currTask.toString());
                            System.out.println("____________________________________________________________");
                            input = scanner.nextLine();
                            continue;
                        } else if (parts[0].equals("unmark")) {
                            if (!currTask.isDone) {
                                throw new TikaException("Task is already unmarked.");
                            }
                            currTask.toggleIsDone();
                            storage.save(list);
                            System.out.println("____________________________________________________________");
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println("  " + currTask.toString());
                            System.out.println("____________________________________________________________");
                            input = scanner.nextLine();
                            continue;
                        } else if (parts[0].equals("delete")) {
                            count--;
                            storage.save(list);
                            System.out.println("____________________________________________________________");
                            System.out.println("Noted. I've removed this task:");
                            System.out.println("  " + currTask.toString());
                            System.out.println("Now you have " + count + " tasks in the list.");
                            System.out.println("____________________________________________________________");
                            list.remove(number - 1);
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
                            throw new TikaException("List is empty... :(");
                        } else {
                            System.out.println("____________________________________________________________");
                            for (int i = 1; i <= count; i++) {
                                Task currTask = list.get(i - 1);
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
                                if (input.substring("todo".length()).isEmpty()) {
                                    throw new TikaException("The description of a todo cannot be empty.");
                                }
                                description = input.substring("todo ".length());
                                newTask = new ToDo(description);
                                list.add(newTask);
                                break;
                            case "deadline":
                                if (input.substring("deadline".length()).isEmpty()) {
                                    throw new TikaException("The description of a deadline cannot be empty.");
                                }
                                try {
                                    String[] split = input.split(" /by ");
                                    if (split.length < 2) {
                                        throw new TikaException(
                                                "Deadline must be: deadline <desc> /by yyyy-mm-dd HHmm"
                                        );
                                    }

                                    description = split[0].substring("deadline ".length());
                                    newTask = new Deadline(description, split[1]);
                                    list.add(newTask);
                                    break;

                                } catch (DateTimeParseException e) {
                                    throw new TikaException(
                                            "Invalid date/time format! Use yyyy-mm-dd HHmm"
                                    );
                                }
                            case "event":
                                if (input.substring("event".length()).isEmpty()) {
                                    throw new TikaException("The description of a event cannot be empty.");
                                }
                                String[] split1 = input.split(" /from ");
                                // do a check for /from present
                                description = split1[0].substring("event ".length());
                                String[] split2 = split1[1].split(" /to ");
                                // do a check for /to present
                                newTask = new Event(description, split2[0], split2[1]);
                                list.add(newTask);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + firstWord);
                        }
                        count++;
                        storage.save(list);
                        System.out.println("____________________________________________________________");
                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + newTask.toString());
                        System.out.println("Now you have " + count + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        input = scanner.nextLine();
                        break;
                    default:
                        throw new TikaException("Not a valid task type! Try again.");
                }
            } catch (TikaException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
                input = scanner.nextLine();
            }
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
