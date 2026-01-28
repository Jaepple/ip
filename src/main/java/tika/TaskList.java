package tika;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void remove(int index) {
        tasks.remove(index);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns a list of tasks that contain the given keyword in their description.
     *
     * @param keyword Keyword to search for
     * @return ArrayList of matching tasks
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(task);
            }
        }
        return matches;
    }
}