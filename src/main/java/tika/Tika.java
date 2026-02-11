package tika;

import java.util.ArrayList;

public class Tika {
    static final int MIN_INDEXED_COMMAND_LENGTH = 2;
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
        if (input.equals("bye")) {
            shouldExit = true;
            return ui.showBye();
        }

        try {
            String firstWord = Parser.getCommandWord(input);

            // Handle indexed commands early
            if (firstWord.equals("mark") || firstWord.equals("unmark") || firstWord.equals("delete")) {
                return handleIndexedCommands(firstWord, input);
            }

            // Handle other commands
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

    private String handleIndexedCommands(String command, String input) throws TikaException {
        boolean isValidIndexCommand = Parser.length(input) != MIN_INDEXED_COMMAND_LENGTH;
        if (isValidIndexCommand) {
            throw new TikaException("Not a valid command!");
        }

        int taskNumber = Parser.parseIndex(input);
        int taskListIndex = taskNumber - 1;

        if (taskNumber > taskList.size()) {
            throw new TikaException("Not a valid task!");
        }

        Task targetTask = taskList.get(taskListIndex);

        switch (command) {
        case "mark":
            if (targetTask.isDone) {
                throw new TikaException("Task is already marked.");
            }
            targetTask.toggleIsDone();
            saveTasks();
            return ui.markTask(targetTask);

        case "unmark":
            if (!targetTask.isDone) {
                throw new TikaException("Task is already unmarked.");
            }
            targetTask.toggleIsDone();
            saveTasks();
            return ui.unmarkTask(targetTask);

        case "delete":
            taskList.remove(taskListIndex);
            saveTasks();
            return ui.deleteTask(targetTask, taskList.size());

        default:
            throw new TikaException("Command not recognised. Try again!");
        }
    }

    private void saveTasks() {
        storage.save(taskList.getTasks());
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
        ArrayList<Task> taskMatches = taskList.find(keyword);

        if (taskMatches.isEmpty()) {
            return "No matching tasks found.";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the matching tasks in your list:\n");
            for (int i = 0; i < taskMatches.size(); i++) {
                sb.append(i + 1).append(".").append(taskMatches.get(i)).append("\n");
            }
            return sb.toString().trim();
        }
    }

    private String addTask(String input) throws TikaException {
        Task newTask = Parser.parseTask(input);
        taskList.add(newTask);
        saveTasks();
        return ui.addTask(newTask, taskList.size());
    }

    public boolean shouldExit() {
        return shouldExit;
    }
}
