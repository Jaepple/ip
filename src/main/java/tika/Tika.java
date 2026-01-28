package tika;

/**
 * The main class of the Tika chatbot application.
 *
 * <p>This class is responsible for:
 * <ul>
 *     <li>Starting the chatbot and greeting the user</li>
 *     <li>Reading user commands in a loop until the user exits</li>
 *     <li>Handling task operations such as add, delete, mark, unmark</li>
 *     <li>Saving and loading tasks from disk via Storage</li>
 * </ul>
 */

public class Tika {
    /**
     * Entry point of the Tika chatbot application.
     * Initializes storage, loads existing tasks, and starts the user interaction loop.
     *
     * @param args Command line arguments (not used)
     */
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
