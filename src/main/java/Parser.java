import java.time.format.DateTimeParseException;

public class Parser {

    public static String getCommandWord(String input) {
        return input.split(" ")[0];
    }

    public static int parseIndex(String input) throws TikaException {
        return Integer.parseInt(input.split(" ")[1]);
    }

    public static int length(String input) {
        String[] parts = input.split(" ");
        return parts.length;
    }

    public static Task parseTask(String input) throws TikaException {
        String command = getCommandWord(input);

        switch (command) {
            case "todo":
                if (input.length() <= 5) {
                    throw new TikaException("The description of a todo cannot be empty.");
                }
                return new ToDo(input.substring(5));

            case "deadline":
                try {
                    String[] split = input.split(" /by ");
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
                String[] split1 = input.split(" /from ");
                String[] split2 = split1[1].split(" /to ");
                return new Event(
                        split1[0].substring(6),
                        split2[0],
                        split2[1]
                );

            default:
                throw new TikaException("Not a valid task type! Try again.");
        }
    }
}