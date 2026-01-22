import java.util.Scanner;

public class Tika {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] list = new String[100];
        int count = 0;

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Tika");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            switch (input) {
                case "list":
                    if (count == 0) {
                        System.out.println("____________________________________________________________");
                        System.out.println("List is empty... :(");
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("____________________________________________________________");
                        for (int i = 1; i <= count; i++) {
                            System.out.println(i + ". " + list[i - 1]);
                        }
                        System.out.println("____________________________________________________________");
                    }
                    input = scanner.nextLine();
                    break;
                default:
                    System.out.println("____________________________________________________________");
                    list[count] = input;
                    count++;
                    System.out.println("added: " + input);
                    System.out.println("____________________________________________________________");
                    input = scanner.nextLine();
                    break;
            }
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
