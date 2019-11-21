import Clients.Client;
import Clients.Individual;
import Clients.LegalEntity;
import Clients.SelfEmployed;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Client client;

        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("Для создания новго клиента выберите категорию клиента. Введите:\n" +
                    "\tI для физ. лиц" +
                    "\n\tL для юр. лиц" +
                    "\n\tS для ИП");

            while (true) {
                String userInput = scanner.nextLine().trim();
                int initialBalance;
                if (userInput.equalsIgnoreCase("I")) {
                    System.out.println("Введите сумму первоначального взноса (целое число без пробелов)");
                    initialBalance = Integer.parseInt(scanner.nextLine());
                    client = new Individual(initialBalance);
                    break;
                } else if (userInput.equalsIgnoreCase("L")) {
                    System.out.println("Введите сумму первоначального взноса (целое число без пробелов)");
                    initialBalance = Integer.parseInt(scanner.nextLine());
                    client = new LegalEntity(initialBalance);
                    break;
                } else if (userInput.equalsIgnoreCase("S")) {
                    System.out.println("Введите сумму первоначального взноса (целое число без пробелов)");
                    initialBalance = Integer.parseInt(scanner.nextLine());
                    client = new SelfEmployed(initialBalance);
                    break;
                } else {
                    System.out.println("Ошибка ввода данных! Повторите попытку!");
                }
            }

            while (true) {
                System.out.println("Для пополнения баланса введите TOP, для снятия денег введите WITHDRAW\n" +
                        "для просмотра остатка средств введите BALANCE\n" +
                        "для создания нового клиента введите NEW");
                String userInput = scanner.nextLine().trim();
                int operationalAmount;
                if (userInput.equalsIgnoreCase("TOP")) {
                    System.out.println("Введите сумму операции (целое число без пробелов)");
                    operationalAmount = Integer.parseInt(scanner.nextLine());
                    client.topUpBalance(operationalAmount);
                } else if (userInput.equalsIgnoreCase("WITHDRAW")) {
                    System.out.println("Введите сумму операции (целое число без пробелов)");
                    operationalAmount = Integer.parseInt(scanner.nextLine());
                    client.withdraw(operationalAmount);
                } else if (userInput.equalsIgnoreCase("BALANCE")) {
                    client.checkBalance();
                } else if (userInput.equalsIgnoreCase("NEW")) {
                    break;
                } else {
                    System.out.println("Ошибка ввода данных! Повторите попытку!");
                }
            }

        }
    }
}
