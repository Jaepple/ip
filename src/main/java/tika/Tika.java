package tika;

import java.util.ArrayList;

public class Tika {
    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private boolean shouldExit = false;

    public Tika() {
        ui = new Ui();
        storage = new Storage();
        taskList = new TaskList(storage.load());
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        if (!input.equals("bye")) {
            try {
                String firstWord = Parser.getCommandWord(input);

                if (firstWord.equals("mark") || firstWord.equals("unmark") || firstWord.equals("delete")) {
                    return handleIndexedCommands(firstWord, input);
                }

                switch (firstWord) {
                case "list":
                    return listTasks();

                case "find":
                    return findTasks(input);

                case "todo":
                case "deadline":
                case "event":
                    return addTask(input);

                default:
                    throw new TikaException("Not a valid task type! Try again.");
                }

            } catch (TikaException e) {
                return ui.showMessage(e.getMessage());
            }
        }
        shouldExit = true;
        return ui.showBye();
    }

    private String handleIndexedCommands(String command, String input) throws TikaException {
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
            return ui.markTask(task);

        case "unmark":
            if (!task.isDone) {
                throw new TikaException("Task is already unmarked.");
            }
            task.toggleIsDone();
            storage.save(taskList.getTasks());
            return ui.unmarkTask(task);

        case "delete":
            taskList.remove(number - 1);
            storage.save(taskList.getTasks());
            return ui.deleteTask(task, taskList.size());

        default:
            throw new TikaException("Command not recognised. Try again!");
        }
    }

    private String listTasks() throws TikaException {
        if (taskList.size() == 0) {
            throw new TikaException("List is empty... :(");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= taskList.size(); i++) {
            sb.append(i).append(".").append(taskList.get(i - 1)).append("\n");
        }

        return sb.toString().trim();
    }

    private String findTasks(String input) throws TikaException {
        String keyword = Parser.parseKeyword(input);
        ArrayList<Task> matches = taskList.find(keyword);

        if (matches.isEmpty()) {
            return "No matching tasks found.";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matches.size(); i++) {
                sb.append(i + 1).append(".").append(matches.get(i)).append("\n");
            }
            return sb.toString().trim();
        }
    }

    private String addTask(String input) throws TikaException {
        Task task = Parser.parseTask(input);
        taskList.add(task);
        storage.save(taskList.getTasks());
        return ui.addTask(task, taskList.size());
    }

    public boolean shouldExit() {
        return shouldExit;
    }
}
