package tika;

import java.time.format.DateTimeParseException;

public class Parser {

    public static String getCommandWord(String input) {
        return input.split(" ")[0];
    }

    public static int parseIndex(String input) throws TikaException {
        try {
            return Integer.parseInt(input.split(" ")[1]);
        } catch (NumberFormatException e) {
            throw new TikaException("Invalid index! Must be an integer.");
        }
    }

    public static int length(String input) {
        String[] parts = input.split(" ");
        return parts.length;
    }

    public static String parseKeyword(String input) throws TikaException {
        String[] parts = input.split(" ", 2);

        if (parts.length < 2 || parts[1].isBlank()) {
            throw new TikaException("Please provide a keyword to search for.");
        }

        return parts[1].trim();
    }

    public static Task parseTask(String input) throws TikaException {
        String command = getCommandWord(input);

        switch (command) {
            case "todo":
                if (input.trim().length() <= 5) {
                    throw new TikaException("The description of a todo cannot be empty.");
                }
                return new ToDo(input.substring(5));

            case "deadline":
                try {
                    String[] split = input.trim().split(" /by ");
                    if (split.length < 2) {
                        throw new TikaException(
                                "Deadline must be: deadline <desc> /by yyyy-mm-dd HHmm");
                    }
                    return new Deadline(
                            split[0].substring(9),
                            split[1]
                    );
                } catch (DateTimeParseException e) {
                    throw new TikaException(
                            "Invalid date/time format! Use yyyy-mm-dd HHmm");
                }

            case "event":
                String[] firstSplit = input.trim().split(" /from ");
                if (firstSplit.length < 2) {
                    throw new TikaException(
                            "Event must be: event <desc> /from <start> /to <end>");
                }
                String[] secondSplit = firstSplit[1].split(" /to ");
                if (secondSplit.length < 2) {
                    throw new TikaException(
                            "Event must be: event <desc> /from <start> /to <end>");
                }
                return new Event(
                        firstSplit[0].substring(6),
                        secondSplit[0],
                        secondSplit[1]
                );

            default:
                throw new TikaException("Not a valid task type! Try again.");
        }
    }
}