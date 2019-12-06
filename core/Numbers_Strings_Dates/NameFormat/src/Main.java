import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите Фамилию Имя и Отчество");

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        while (checkWordsCount(userInput.trim()) || containsInvalidChar(userInput)){
            System.out.println("Неверный ввод данных,\nпопробуйте снова");
            userInput = scanner.nextLine();
        }

        String[] name = userInput.trim().split("\\s");

        System.out.println("Фамилия: " + name[0]);
        System.out.println("Имя: " + name[1]);
        System.out.println("Отчество: " + name[2]);
    }

    private static boolean checkWordsCount(String testString){
        if (!testString.contains(" ") || testString.length() - testString.replace(" ", "").length() != 2) {
            return true;
        }

        return false;
    }

    private static boolean containsInvalidChar(String testString){
        boolean result = true;

        for (int i = 0; i < testString.length(); i++){
            char testChar = testString.charAt(i);
            if(((int)testChar < 1104 && (int)testChar > 1039) ||
                    ((int)testChar > 64 && (int)testChar < 92) ||
                    ((int)testChar > 96 && (int)testChar < 123) || testChar == ' '){
                result = false;
            }
            else {
                result = true;
                break;
            }
        }

        return result;
    }
}
