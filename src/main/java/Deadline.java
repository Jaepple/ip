public class Deadline extends Task {
    protected String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D][" + this.getStatusIcon() + "] " + this.description + " (by: " + this.deadline + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.deadline;
    }
}
