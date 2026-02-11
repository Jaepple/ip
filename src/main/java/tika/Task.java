package tika;


/**
 * Represents a generic task in the Tika chatbot.
 *
 * <p>Each task has a description and a status indicating whether it is done.
 * This class serves as a base for specific types of tasks, such as {@link ToDo},
 * {@link Deadline} and {@link Event}.</p>
 */
public abstract class Task {
    /** Description of the task. */
    protected String description;

    /** Whether the task is done. */
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * The task is initially not done.
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a status icon for the task.
     *
     * @return "X" if the task is done, otherwise a blank space " "
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Toggles the completion status of the task.
     * If the task was done, it becomes not done, and vice versa.
     */
    public void toggleIsDone() {
        this.isDone = !isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description
     */
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task)) return false;

        Task otherTask = (Task) obj;
        return this.description.equalsIgnoreCase(otherTask.description);
    }

    /**
     * Returns a string representation of the task suitable for saving to a file.
     * Each subclass of Task must implement this method to define its own format.
     *
     * @return A string representation of the task for storage
     */
    public abstract String toFileString();
}
