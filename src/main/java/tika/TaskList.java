package tika;

import java.util.ArrayList;

/**
 * Represents a list of tasks in the Tika chatbot.
 *
 * <p>This class wraps an {@link ArrayList} of {@link Task} objects and provides
 * methods to manipulate the task list, such as adding, removing, and retrieving tasks.</p>
 */
public class TaskList {
    /** Internal list of tasks. */
    private final ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks An ArrayList of tasks to initialize the TaskList
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The index of the task to retrieve
     * @return The task at the given index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task The task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes the task at the specified index.
     *
     * @param index The index of the task to remove
     */
    public void remove(int index) {
        tasks.remove(index);
    }

    /**
     * Returns the internal list of tasks.
     *
     * @return The ArrayList of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}