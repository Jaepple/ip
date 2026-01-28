package tika;

public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E][" + this.getStatusIcon() + "] " + this.description + " (from: " + this.from + " to: " + this.to + ")";
    }

    public String toFileString() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description
                + " | " + this.from + " | " + this.to;
    }
}
