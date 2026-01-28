public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void toggleIsDone() {
        this.isDone = !isDone;
    }

    public String getDescription() {
        return this.description;
    }

    public abstract String toFileString();
}
