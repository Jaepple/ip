package tika;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task in the Tika chatbot.
 *
 * <p>A Deadline has a description and a due date/time.
 * It can also be marked as done or not done.</p>
 *
 * <p>Example of how it is displayed to the user:
 * <pre>
 * [D][ ] return book (by: Jun 06 2019 6:00 PM)
 * </pre>
 * </p>
 */
public class Deadline extends Task {
    /** The due date and time of the deadline. */
    private final LocalDateTime deadline;

    /** Formatter for parsing input date/time strings. */
    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /** Formatter for displaying date/time to the user. */
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a");

    /**
     * Constructs a new Deadline task.
     *
     * @param description Description of the deadline
     * @param deadline The due date/time in "yyyy-MM-dd HHmm" format
     */
    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = LocalDateTime.parse(deadline, INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the deadline for display to the user.
     *
     * @return The string representation of the deadline
     */
    @Override
    public String toString() {
        return "[D][" + this.getStatusIcon() + "] " + this.description + " (by: " + this.deadline.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns a string representation of the deadline for saving to file.
     *
     * <p>The format is: "D | status | description | yyyy-MM-dd HHmm"</p>
     *
     * @return The string representation of the deadline for storage
     */
    @Override
    public String toFileString() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.deadline.format(INPUT_FORMAT);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Deadline)) return false;

        Deadline otherTask = (Deadline) obj;

        // Compare description (case-insensitive) and exact deadline
        return this.description.equalsIgnoreCase(otherTask.description)
                && this.deadline.equals(otherTask.deadline);
    }
}
