import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Write you phone number:");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String clearedString = userInput.replaceAll("[^0-9]", "");

        if (clearedString.charAt(0) == '7' || clearedString.charAt(0) == '8') {
            clearedString = clearedString.substring(1);
        }

        System.out.println("+7 " + clearedString.substring(0,3) +
                " " + clearedString.substring(3, 6) +
                "-" + clearedString.substring(6,8) +
                "-" + clearedString.substring(8));
    }
}
