import java.util.Scanner;

public class Main
{
    private static int minIncome = 200000; // переменная задающая нижний допустимы  предел значенний для ввода
    private static int maxIncome = 900000; // переменная задающая верхний допустимый предел значений для ввода

    private static int officeRentCharge = 140000; // переменная задающая сумму издержек на аренду
    private static int telephonyCharge = 12000; // переменная задающая сумму издержек на телефонию
    private static int internetAccessCharge = 7200; // переменная задающая сумму издержек на интернет

    private static int assistantSalary = 45000; // переменная задающая сумму зарплаты ассистента
    private static int financeManagerSalary = 90000; // переменная задающая зарплату финансового менеджера

    private static double mainTaxPercent = 0.24; // переменная задающая % налога
    private static double managerPercent = 0.15; // переменная задающая % от дохода, который получает менеджер

    private static double minInvestmentsAmount = 100000; // переменная задающая минимальную сумму для инвестирования

    public static void main(String[] args)
    {
        // рассчитываем значение minIncome, которое позволяет фирме производить инвестиции
        int minIncome = -1;
        double incomeBeforeTax = minInvestmentsAmount / (1 - mainTaxPercent);
        double minCalculatedIncome = (incomeBeforeTax + calculateFixedCharges())/(1 - managerPercent);
        minIncome = (int) Math.round(minCalculatedIncome);
        if (minIncome < minCalculatedIncome){
            minIncome++;
        }

        // цикл позволяющий программе бесконечно выдавать запрос на расчёт возможности инвестирования исходя из суммы доходов
        while(true)
        {
            System.out.println("Введите сумму доходов компании за месяц " + // Печатаем в консоли строку с запросом на ввод суммы доходов
                "(от 200 до 900 тысяч рублей): ");
            int income = (new Scanner(System.in)).nextInt(); // Считывает из консоли введенное значение

            // Проверяем находится ли введенное в консоли значение в допустимых пределах с помощью метода checkIncomeRange
            if(!checkIncomeRange(income)) {
                continue; //если значение за пределами допустимых значений, выдается сообщение об ошибке, цикл начинается заново и выдается запрос на ввод данных
            }

            double managerSalary = income * managerPercent; // рассчитывется зарплата менеджера
            double pureIncome = income - managerSalary - // рассчитывается прибыль
                calculateFixedCharges();
            double taxAmount = mainTaxPercent * pureIncome; // рассчитывется сумма налога
            double pureIncomeAfterTax = pureIncome - taxAmount; // рассчитвается сумма чистой прибыли

            boolean canMakeInvestments = pureIncomeAfterTax >= // проверяется хватает ли чистой прибыли для того чтобы делать инвестиции
                minInvestmentsAmount;

            System.out.println("Зарплата менеджера: " + managerSalary); // выводит в консоль зарплату менеджера
            System.out.println("Общая сумма налогов: " + // выводит в консоль общую сумму налогов
                (taxAmount > 0 ? taxAmount : 0));
            System.out.println("Компания может инвестировать: " + // выводит в консоль результат проверки на возможность делать инвестиции
                (canMakeInvestments ? "да" : "нет"));
            if(pureIncome < 0) {
                System.out.println("Бюджет в минусе! Нужно срочно зарабатывать!"); // если фирма терпит убытки выводится соответсвующее сообщение в консоли
            }
            System.out.println("Минимальная сумма доходов для инвестиций должна составлять: " + minIncome); // выводим в консоль минимальную сумму доходов компании, чтобы она могла делать инвестиции
        }
    }

    // метод проверки того, находится ли введеное пользователем значение в допустимых пределах
    // возвращающий переменную boolean
    private static boolean checkIncomeRange(int income)
    {
        if(income < minIncome) //если значение меньше нижнего допустимого значение, выводит соответсвующее сообщение в консоли и возвращает значение false
        {
            System.out.println("Доход меньше нижней границы");
            return false;
        }
        if(income > maxIncome) //если значение больше верхнего допустимого значения, выводит соответсвующее сообщениев консоли и возвращает значение false
        {
            System.out.println("Доход выше верхней границы");
            return false;
        }
        return true; // возвращает значение true когда значение введенное в консоли находится в допустимых пределах
    }

    // метод для расчета постоянных издержек
    private static int calculateFixedCharges()
    {
        return officeRentCharge +
                telephonyCharge +
                internetAccessCharge +
                assistantSalary +
                financeManagerSalary; // возвращает сумму постоянных издержек
    }
}
