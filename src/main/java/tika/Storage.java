package tika;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading tasks from a file and saving tasks to a file.
 *
 * <p>This class is responsible for:
 * <ul>
 *     <li>Loading tasks from disk when the chatbot starts</li>
 *     <li>Saving tasks to disk whenever the task list changes</li>
 *     <li>Parsing task strings from the file into Task objects</li>
 * </ul>
 * </p>
 */
public class Storage {
    /** Path to the data file where tasks are stored. */
    private static final String FILE_PATH =
            "data" + File.separator + "tika.txt";

    /**
     * Loads tasks from the file.
     *
     * <p>If the file or its parent folder does not exist, they are created automatically.
     * If the file is empty or contains invalid lines, only valid tasks are loaded.</p>
     *
     * @return An ArrayList of tasks loaded from the file
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(FILE_PATH);
            File parentDir = file.getParentFile();

            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
                return tasks;
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            scanner.close();

        } catch (Exception e) {
            return new ArrayList<>();
        }

        return tasks;
    }

    /**
     * Saves the current task list to the file.
     *
     * @param tasks The list of tasks to save
     */
    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                fw.write(task.toFileString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    /**
     * Parses a line from the file into a Task object.
     *
     * <p>The line must follow the format:
     * <pre>
     * T | 1 | description
     * D | 0 | description | by
     * E | 0 | description | from | to
     * </pre>
     * If the line is malformed, this method returns null.
     * </p>
     *
     * @param line A line from the file
     * @return The corresponding Task object, or null if parsing fails
     */
    private Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task;
            switch (type) {
                case "T":
                    task = new ToDo(description);
                    break;
                case "D":
                    task = new Deadline(description, parts[3]);
                    break;
                case "E":
                    task = new Event(description, parts[3], parts[4]);
                    break;
                default:
                    return null;
            }

            if (isDone) {
                task.toggleIsDone();
            }
            return task;

        } catch (Exception e) {
            return null;
        }
    }
}
