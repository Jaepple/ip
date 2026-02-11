package tika;

/**
 * Represents an event task in the Tika chatbot.
 *
 * <p>An Event has a description, a start time, and an end time.
 * It can also be marked as done or not done.</p>
 *
 * <p>Example of how it is displayed to the user:
 * <pre>
 * [E][X] project meeting (from: Aug 6th 2pm to: Aug 6th 4pm)
 * </pre>
 * </p>
 */
public class Event extends Task {
    /** Start time of the event. */
    protected String from;

    /** End time of the event. */
    protected String to;

    /**
     * Constructs a new Event task.
     *
     * @param description Description of the event
     * @param from Start time of the event
     * @param to End time of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event for display to the user.
     *
     * @return The string representation of the event
     */
    @Override
    public String toString() {
        return "[E][" + this.getStatusIcon() + "] " + this.description + " (from: " + this.from + " to: " + this.to + ")";
    }

    /**
     * Returns a string representation of the event for saving to file.
     *
     * <p>The format is: "E | status | description | from | to"</p>
     *
     * @return The string representation of the event for storage
     */
    @Override
    public String toFileString() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description
                + " | " + this.from + " | " + this.to;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Event)) return false;

        Event otherTask = (Event) obj;

        return this.description.equalsIgnoreCase(otherTask.description)
                && this.from.equals(otherTask.from)
                && this.to.equals(otherTask.to);
    }
}
