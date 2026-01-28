import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH =
            "data" + File.separator + "tika.txt";

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
