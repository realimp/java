import Staff.Operationist;
import Staff.SalesManager;
import Staff.TopManager;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String[] COMMANDS = {"FIRE", "HELP", "HIRE", "LIST", "LOW", "NEXT", "TOP"};
    private static final String[] HIRE_PARAMS = {"o", "s", "t"};

    public static void main(String[] args) {
        Company company = new Company(270);

        System.out.println("В Вашей компании работет " + company.getNumberOfEmployees() + " человек.\n" +
                "Вы можете нанимать и увольниять сотрудников, а также просмотреть список сотрудников\n" +
                "с наибольшими и наименьшим зарплатами по итогом прошлого месяца.\n" +
                "Введите команду HELP чтобы посмотреть как это сделать");

        company.calculateMonthlyBalance();

        Scanner scanner = new Scanner(System.in);
        while (true){
            String userInput = scanner.nextLine().trim();
            if (checkCommandInput(userInput)){
                String command = userInput.contains(" ") ? userInput.substring(0, userInput.indexOf(" ")) : userInput;
                String commandParameter = userInput.contains(" ") ? userInput.substring(userInput.indexOf(" ") + 1) : null;
                if (command.equalsIgnoreCase("HIRE")){
                    if (checkHireParameters(commandParameter)){
                        String parameter = commandParameter.replaceAll("-","");
                        if (parameter.equalsIgnoreCase("o")){
                            int rnd = new Random().nextInt(Company.OPERATIONIST_SALARY.length);
                            company.hireEmployee(new Operationist(Company.SALES_MANAGER_SALARY[rnd]));
                        }
                        else if (parameter.equalsIgnoreCase("s")){
                            int rnd2 = new Random().nextInt(Company.SALES_MANAGER_SALARY.length);
                            company.hireEmployee(new SalesManager(Company.SALES_MANAGER_SALARY[rnd2]));
                        }
                        else {
                            int rnd3 = new Random().nextInt(Company.TOP_MANAGER_SALARY.length);
                            company.hireEmployee(new TopManager(Company.TOP_MANAGER_SALARY[rnd3]));
                        }
                    }
                    else {
                        continue;
                    }
                }
                else if (command.equalsIgnoreCase("FIRE")){
                    if (checkFireParameters(commandParameter)) {
                        String parameter = commandParameter.replaceAll("-", "");
                        if (parameter.equalsIgnoreCase("o")) {
                            company.fireEmployee(-1);
                        } else if (parameter.equalsIgnoreCase("s")) {
                            company.fireEmployee(-2);
                        } else if (parameter.equalsIgnoreCase("t")) {
                            company.fireEmployee(0);
                        }
                        else {
                            company.fireEmployee(Integer.parseInt(commandParameter));
                        }
                    }
                }
                else if (command.equalsIgnoreCase("TOP")){
                    if (commandParameter.matches("[0-9]+")){
                        company.getTopSalaryStaff(Integer.parseInt(commandParameter));
                    }
                    else {
                        System.out.println("Недопустимый параметр!");
                        continue;
                    }
                }
                else if (command.equalsIgnoreCase("LOW")){
                    if (commandParameter.matches("[0-9]+")){
                        company.getLowestSalaryStaff(Integer.parseInt(commandParameter));
                    }
                    else {
                        System.out.println("Недопустимый параметр!");
                        continue;
                    }
                }
                else if (command.equalsIgnoreCase("NEXT")){
                    company.calculateMonthlyBalance();
                }
                else if (command.equalsIgnoreCase("LIST")){
                    company.listAllEmployees();
                }
                else {
                    printHelp();
                }
            }
            else {
                System.out.println("Недопустимая команда! Для просмотра списка команд введите HELP");
            }
        }
    }

    private static boolean checkCommandInput(String testString){
        testString = testString.contains(" ") ? testString.substring(0, testString.indexOf(" ")) : testString;
        for (int i = 0; i < COMMANDS.length; i++){
            if (testString.equalsIgnoreCase(COMMANDS[i])){
                return true;
            }
        }

        return false;
    }

    private static boolean checkHireParameters(String param){
        if (param != null && param.contains("-")){
            for (int i = 0; i < HIRE_PARAMS.length; i++){
                if (param.substring(param.indexOf("-") + 1).equalsIgnoreCase(HIRE_PARAMS[i])){
                    return true;
                }
            }
        }
        System.out.println("Введен недопустимый параметр!");
        return false;
    }

    private static boolean checkFireParameters(String param){
        if (param != null && param.contains("-")){
            for (int i = 0; i < HIRE_PARAMS.length; i++){
                if (param.substring(param.indexOf("-") + 1).equalsIgnoreCase(HIRE_PARAMS[i])){
                    return true;
                }
            }
        }
        if (param.matches("[0-9]+")){
            return true;
        }
        System.out.println("Введен недопустимый параметр!");
        return false;
    }

    private static void printHelp(){
        System.out.println("\tFIRE - уволить сотрудника, используется совместно с одним из параметров, указывающим должность сотрудника" +
                "\n\t\t-o - операционист" +
                "\n\t\t-s - менеджер по продажам" +
                "\n\t\t-t - топ менеджер" +
                "\n\tHELP - справка" +
                "\n\tHIRE - нанять сотрудника, используется совместно с одним из параметров, указывающим должность сотрудника" +
                "\n\t\t-o - операционист" +
                "\n\t\t-s - менеджер по продажам" +
                "\n\t\t-t - топ менеджер" +
                "\n\tLIST - отображает список всех сотрудников с указание номера в табели организации" +
                "\n\tLOW - выводит список сотрудников с самой низкой месячной зарплатой," +
                "\n\t\tиспользуется вместе с параметром указывающим какое число сотрудников нужно показать" +
                "\n\tNEXT - проверить финансовые успехи компании в следующем месяце после того как Вы наняли новых работников" +
                "или уволили кого-то из персонала" +
                "\n\tTOP - выводит список сотрудников с самой высокой зарплатой за месяц," +
                "\n\t\tиспользуется вместе с параметром указывающим какое число сотрудников нужно показать");
    }
}
