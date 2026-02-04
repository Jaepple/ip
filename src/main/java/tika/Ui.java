package tika;


public class Ui {

    public String showWelcome() {
        return "Hello! I'm Tika\nWhat can I do for you?";
    }

    public String showMessage(String message) {
        return message;
    }

    public String markTask(Task currTask) {
        return "Nice! I've marked this task as done:\n  " + currTask.toString();
    }

    public String unmarkTask(Task currTask) {
        return "OK, I've marked this task as not done yet:\n  " + currTask.toString();
    }

    public String deleteTask(Task currTask, int count) {
        return "Noted. I've removed this task:\n  " + currTask.toString()
                + "\nNow you have " + count + " tasks in the list.";
    }

    public String addTask(Task newTask, int count) {
        return "Got it. I've added this task:\n  " + newTask.toString()
                + "\nNow you have " + count + " tasks in the list.";
    }

    public String showBye() {
        return "Bye. Hope to see you again soon!";
    }
}
