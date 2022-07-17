import java.util.Scanner;

public class Utils {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }

    public static int getIntegerInput(String expression) {
        do {
            System.out.print("Please enter input number for \"" + expression + "\": ");
            try {
                return Integer.parseInt(Utils.getScanner().nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please try again.");
            }
        } while (true);
    }
}
