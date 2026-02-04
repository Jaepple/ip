package tika;
import java.util.ArrayList;

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
    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    /**
     * Constructs the Tika chatbot application.
     * Initializes UI, storage, and loads existing tasks.
     */
    public Tika() {
        ui = new Ui();
        storage = new Storage();
        taskList = new TaskList(storage.load());
    }

    /**
     * Entry point of the application.
     */
    public static void main(String[] args) {
        new Tika().run();
    }

    /**
     * Runs the main user interaction loop.
     */
    public void run() {
        ui.showWelcome();
        String input = ui.readCommand();

        while (!input.equals("bye")) {
            try {
                String firstWord = Parser.getCommandWord(input);

                if (firstWord.equals("mark") || firstWord.equals("unmark") || firstWord.equals("delete")) {
                    handleIndexedCommands(firstWord, input);
                    input = ui.readCommand();
                    continue;
                }

                switch (firstWord) {
                case "list":
                    listTasks();
                    break;

                case "find":
                    findTasks(input);
                    break;

                case "todo":
                case "deadline":
                case "event":
                    addTask(input);
                    break;

                default:
                    throw new TikaException("Not a valid task type! Try again.");
                }

            } catch (TikaException e) {
                ui.showMessage(e.getMessage());
            }

            input = ui.readCommand();
        }

        ui.showBye();
    }

    private void handleIndexedCommands(String command, String input) throws TikaException {
        if (Parser.length(input) != 2) {
            throw new TikaException("Not a valid command!");
        }
        int number = Parser.parseIndex(input);

        if (number > taskList.size()) {
            throw new TikaException("Not a valid task!");
        }

        Task task = taskList.get(number - 1);

        switch (command) {
        case "mark":
            if (task.isDone) {
                throw new TikaException("Task is already marked.");
            }
            task.toggleIsDone();
            storage.save(taskList.getTasks());
            ui.markTask(task);
            break;

        case "unmark":
            if (!task.isDone) {
                throw new TikaException("Task is already unmarked.");
            }
            task.toggleIsDone();
            storage.save(taskList.getTasks());
            ui.unmarkTask(task);
            break;

        case "delete":
            taskList.remove(number - 1);
            storage.save(taskList.getTasks());
            ui.deleteTask(task, taskList.size());
            break;

        default:
            break;
        }
    }

    private void listTasks() throws TikaException {
        if (taskList.size() == 0) {
            throw new TikaException("List is empty... :(");
        }

        ui.showLine();
        for (int i = 1; i <= taskList.size(); i++) {
            System.out.println(i + "." + taskList.get(i - 1));
        }
        ui.showLine();
    }

    private void findTasks(String input) throws TikaException {
        String keyword = Parser.parseKeyword(input);
        ArrayList<Task> matches = taskList.find(keyword);

        ui.showLine();
        if (matches.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + "." + matches.get(i));
            }
        }
        ui.showLine();
    }

    private void addTask(String input) throws TikaException {
        Task task = Parser.parseTask(input);
        taskList.add(task);
        storage.save(taskList.getTasks());
        ui.addTask(task, taskList.size());
    }
}
