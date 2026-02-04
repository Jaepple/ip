package tika;

public class TikaChat {
    private static Ui ui;
    private static Storage storage;
    private static TaskList taskList;

    public static void main(String[] args) {
        ui = new Ui();
        storage = new Storage();
        taskList = new TaskList(storage.load());
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }
}
