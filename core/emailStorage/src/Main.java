import java.util.HashSet;
import java.util.Scanner;

public class Main
{
    private static final String[] COMMANDS = {"ADD", "LIST"};
    private static HashSet<String> emails = new HashSet<>();

    public static void main(String[] args) {

        System.out.println("Для добавления Email воспользуйтесь командой ADD.\n" +
                "Чтобы просмотреть список адресов введите команду LIST");

        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (true){
            System.out.println("Введите команду:");
            userInput = scanner.nextLine();

            if (checkCommandInput(userInput)){
                String command;
                String commandParameter = null;
                if (userInput.contains(" ")) {
                    command = userInput.substring(0, userInput.indexOf(" "));
                    commandParameter = userInput.substring(userInput.indexOf(" ")).trim();
                }
                else {
                    command = userInput;
                }

                if (command.equalsIgnoreCase("ADD")){
                    if (validateEmail(commandParameter)){
                        emails.add(commandParameter);
                    }
                    else {
                        System.out.println("Введен не корректный email, повторите попытку.");
                    }
                }
                else
                {
                    if (emails.size() > 0) {
                        for (String email : emails) {
                            System.out.println(email);
                        }
                    }
                    else
                    {
                        System.out.println("Список адресов пуст. Воспользуйтесь командой ADD, чтобы добавить email.");
                    }
                }
            }
            else {
                System.out.println("Введена недопустимая команда! Попробуйте снова.");
            }
        }

    }

    private static boolean checkCommandInput(String testString)
    {
        boolean result = false;

        if(testString.contains(" ")) {
            for (int i = 0; i < COMMANDS.length; i++) {
                if (testString.substring(0, testString.indexOf(" ")).equalsIgnoreCase(COMMANDS[i])) {
                    result = true;
                }
            }
        }
        else if (testString.equalsIgnoreCase("LIST")){
            result = true;
        }

        return result;
    }

    private static boolean validateEmail (String testString)
    {
        boolean result = false;

        if (testString.matches(".+@.+\\..+") && !testString.contains(" ")){
            result = true;
        }

        return result;
    }
}
