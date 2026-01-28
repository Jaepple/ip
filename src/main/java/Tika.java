import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Tika {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage();
        TaskList taskList = new TaskList(storage.load());

        ui.showWelcome();
        String input = ui.readCommand();

        while (!input.equals("bye")) {
            try {
                String[] parts = input.split(" ");
                String firstWord = parts[0];
                if (parts.length == 2) {
                    try {
                        int number = Integer.parseInt(parts[1]);
                        if (number > taskList.size()) { // to fix: should allow todo 3 as a task
                            throw new TikaException("Not a valid task!");
                        }
                        Task currTask = taskList.get(number - 1);
                        if (parts[0].equals("mark")) {
                            if (currTask.isDone) {
                                throw new TikaException("Task is already marked.");
                            }
                            currTask.toggleIsDone();
                            storage.save(taskList.getTasks());
                            ui.markTask(currTask);
                            input = ui.readCommand();
                            continue;
                        } else if (parts[0].equals("unmark")) {
                            if (!currTask.isDone) {
                                throw new TikaException("Task is already unmarked.");
                            }
                            currTask.toggleIsDone();
                            storage.save(taskList.getTasks());
                            ui.unmarkTask(currTask);
                            input = ui.readCommand();
                            continue;
                        } else if (parts[0].equals("delete")) {
                            storage.save(taskList.getTasks());
                            taskList.remove(number - 1);
                            ui.deleteTask(currTask, taskList.size());
                            input = ui.readCommand();
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("plop");
                    }
                }

                switch (firstWord) {
                    case "list":
                        if (taskList.size() == 0) {
                            throw new TikaException("List is empty... :(");
                        } else {
                            ui.showLine();
                            for (int i = 1; i <= taskList.size(); i++) {
                                Task currTask = taskList.get(i - 1);
                                System.out.println(i + "." + currTask.toString());
                            }
                            ui.showLine();
                        }
                        input = ui.readCommand();
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
                                taskList.add(newTask);
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
                                    taskList.add(newTask);
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
                                taskList.add(newTask);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + firstWord);
                        }
                        storage.save(taskList.getTasks());
                        ui.addTask(newTask, taskList.size());
                        input = ui.readCommand();
                        break;
                    default:
                        throw new TikaException("Not a valid task type! Try again.");
                }
            } catch (TikaException e) {
                ui.showMessage(e.getMessage());
                input = ui.readCommand();
            }
        }
        ui.showBye();
    }
}
