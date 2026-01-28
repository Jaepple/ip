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
                String firstWord = Parser.getCommandWord(input);
                if (Parser.length(input) == 2) {
                    try {
                        int number = Parser.parseIndex(input);
                        if (number > taskList.size()) { // to fix: should allow todo 3 as a task
                            throw new TikaException("Not a valid task!");
                        }
                        Task currTask = taskList.get(number - 1);
                        if (firstWord.equals("mark")) {
                            if (currTask.isDone) {
                                throw new TikaException("Task is already marked.");
                            }
                            currTask.toggleIsDone();
                            storage.save(taskList.getTasks());
                            ui.markTask(currTask);
                            input = ui.readCommand();
                            continue;
                        } else if (firstWord.equals("unmark")) {
                            if (!currTask.isDone) {
                                throw new TikaException("Task is already unmarked.");
                            }
                            currTask.toggleIsDone();
                            storage.save(taskList.getTasks());
                            ui.unmarkTask(currTask);
                            input = ui.readCommand();
                            continue;
                        } else if (firstWord.equals("delete")) {
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
                        Task newTask = Parser.parseTask(input);
                        taskList.add(newTask);
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
