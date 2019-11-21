import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String[] COMMANDS = {"ADD", "DELETE", "EDIT", "LIST"};
    private static ArrayList<String> toDo = new ArrayList<>();

    public static void main(String[] args)
    {
        System.out.println("Добро пожаловать в ToDoList!\n" +
                "Вы можете управлять списокм задач используюя следующие команды:\n" +
                "\tADD - добавить новую задачу\n" +
                "\tADD i - добавить новую задачу на позицию i в списке\n" +
                "\tEDIT i - изменить задачу с индексом i\n" +
                "\tDELETE i - удалить задачу с индексом i\n" +
                "\tLIST - вывести список задач.");

        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (true){
            System.out.println("Введите команду:");
            userInput = scanner.nextLine();
            if (checkCommandInput(userInput))
            {
                String command;
                if (userInput.contains(" ")){
                    command = userInput.substring(0, userInput.indexOf(" "));
                }
                else {
                    command = userInput;
                }

                String commandParameter = userInput.substring(userInput.indexOf(" ") + 1);

                int commandIndexParameter = -1;
                String commandStringParameter = null;

                if (commandParameter.contains(" ")){
                    if (commandParameter.substring(0, commandParameter.indexOf(" ")).matches("[0-9]+")){
                        commandIndexParameter = Integer.parseInt(commandParameter.substring(0, commandParameter.indexOf(" ")));
                        commandStringParameter = commandParameter.substring(commandParameter.indexOf(" ") + 1);
                    }
                    else {
                        commandStringParameter = commandParameter;
                    }
                }
                else if (commandParameter.matches("[0-9]+"))
                {
                    commandIndexParameter = Integer.parseInt(commandParameter);
                }
                else {
                    commandStringParameter = commandParameter;
                }


                if (command.equals("ADD"))
                {
                    if (commandIndexParameter != -1 && commandIndexParameter <= toDo.size()){
                        toDo.add(commandIndexParameter, commandStringParameter);
                    }
                    else {
                        toDo.add(commandStringParameter);
                    }
                }
                else if (command.equals("DELETE"))
                {
                    if (commandIndexParameter != -1 && commandIndexParameter < toDo.size()){
                        toDo.remove(commandIndexParameter);
                    }
                    else {
                        System.out.println("Введен неверный параметр");
                    }
                }
                else if (command.equals("EDIT"))
                {
                    if (commandIndexParameter != -1 && commandIndexParameter < toDo.size()){
                        toDo.set(commandIndexParameter, commandStringParameter);
                    }
                    else {
                        System.out.println("Введен неверный параметр");
                    }
                }
                else
                {
                    for (int i = 0; i < toDo.size(); i++){
                        System.out.println(i + " - " + toDo.get(i));
                    }
                }
            }
            else
            {
                System.out.println("Неверная команда, попробуйте снова.");
            }
        }
    }

    private static boolean checkCommandInput(String testString){
        boolean result = false;

        if(testString.contains(" ")){
            for (int i = 0; i < COMMANDS.length; i++){
                if (testString.substring(0, testString.indexOf(" ")).equals(COMMANDS[i])){
                    result = true;
                }
            }
        }

        if (testString.equals("LIST")){
            result = true;
        }

        return result;
    }

}
