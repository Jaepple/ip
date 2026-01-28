package tika;

/**
 * Represents a To-Do task in the Tika chatbot.
 *
 * <p>A ToDo has a description and can be marked as done or not done.</p>
 *
 * <p>Example of how it is displayed to the user:
 * <pre>
 * [T][ ] read book
 * </pre>
 * </p>
 */
public class ToDo extends Task {
    /**
     * Constructs a new ToDo task.
     *
     * @param description Description of the ToDo
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the ToDo for display to the user.
     *
     * @return The string representation of the ToDo
     */
    @Override
    public String toString() {
        return "[T][" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns a string representation of the ToDo for saving to file.
     *
     * <p>The format is: "T | status | description"</p>
     *
     * @return The string representation of the ToDo for storage
     */
    @Override
    public String toFileString() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }
}
