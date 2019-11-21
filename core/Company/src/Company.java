import java.util.*;

import Staff.Employee;
import Staff.Operationist;
import Staff.SalesManager;
import Staff.TopManager;

public class Company {

    public static final int[] OPERATIONIST_SALARY = {18000, 19000, 20000, 21000, 22000, 23000, 24000, 25000};
    public static final int[] SALES_MANAGER_SALARY = {40000, 45000, 50000};
    public static final int[] TOP_MANAGER_SALARY = {100000, 150000, 200000};

    private HashMap<Integer, Employee> staf = new HashMap<>();

    private int employeeIdCounter = 0;

    private long companyMonthlyBalance;
    private int topManagementPercent = 1;
    private int salesManagersPercent = 75;
    private int topManagerBonus = 200000;

    Company(int numberOfEmployees){
        int topManagersCount = Math.round(numberOfEmployees * topManagementPercent / 100);
        int salesManagersCount = Math.round(numberOfEmployees * salesManagersPercent / 100);
        int operationistsCount = numberOfEmployees - topManagersCount - salesManagersCount;

        for (int i = 0; i < operationistsCount; i++){
            int rnd = new Random().nextInt(OPERATIONIST_SALARY.length);
            hireEmployee(new Operationist(OPERATIONIST_SALARY[rnd]));
        }

        for (int i = 0; i < topManagersCount; i++){
            int rnd2 = new Random().nextInt(TOP_MANAGER_SALARY.length);
            hireEmployee(new TopManager(TOP_MANAGER_SALARY[rnd2]));
        }

        for (int i = 0; i < salesManagersCount; i++){
            int rnd3 = new Random().nextInt(SALES_MANAGER_SALARY.length);
            hireEmployee(new SalesManager(SALES_MANAGER_SALARY[rnd3]));
        }
    }

    void hireEmployee(Employee employee)
    {
        staf.put(++employeeIdCounter, employee);
    }

    void fireEmployee(int param)
    {
        if (param == -1){
            for (int key : staf.keySet()){
                if (staf.get(key).getPosition().equalsIgnoreCase("Менеджер по продажам")){
                    staf.remove(key);
                    break;
                }
            }
        }
        else if (param == -2){
            for (int key : staf.keySet()){
                if (staf.get(key).getPosition().equalsIgnoreCase("Операционист")){
                    staf.remove(key);
                    break;
                }
            }
        }
        else if (param == 0){
            for (int key : staf.keySet()) {
                if (staf.get(key).getPosition().equalsIgnoreCase("Топ менеджер")){
                    staf.remove(key);
                    break;
                }
            }
        }
        else {
            staf.remove(param);
        }
    }

    void getTopSalaryStaff(int count){
        ArrayList<Employee> sortedList = new ArrayList<>();
        sortedList.addAll(staf.values());
        sortedList.sort((e1, e2) -> new Integer(e2.getMonthlySalary()).compareTo(e1.getMonthlySalary()));

        for (int i = 0; i < count; i++){
            Employee employee = sortedList.get(i);
            printEmployeeDetails(employee);
        }
    }

    void getLowestSalaryStaff(int count) {
        ArrayList<Employee> sortedList = new ArrayList<>();
        sortedList.addAll(staf.values());
        sortedList.sort((e1, e2) -> new Integer(e1.getMonthlySalary()).compareTo(e2.getMonthlySalary()));
        for (int i = 0; i < count; i++){
            Employee employee = sortedList.get(i);
            printEmployeeDetails(employee);
        }
    }

    public int getNumberOfEmployees() {
        return staf.size();
    }

    private void printEmployeeDetails(Employee employee){
        String salaryString = " - " + (employee.getMonthlySalary() / 100) +
                (employee.getMonthlySalary() % 100 < 10 ?
                        (",0" + (employee.getMonthlySalary() % 100)) : ("," + (employee.getMonthlySalary() % 100)) );
        System.out.println(employee.getName() + " - " + employee.getPosition() + salaryString);
    }

    public void calculateMonthlyBalance(){
        companyMonthlyBalance = 0;
        for (int key : staf.keySet()){
            if (staf.get(key).getPosition().equalsIgnoreCase("Менеджер по продажам")){
                companyMonthlyBalance += (((SalesManager) staf.get(key)).earnMoney() * 100);
            }
            companyMonthlyBalance -= staf.get(key).getMonthlySalary();
        }

        if (companyMonthlyBalance > 1000000000){
            for (int key : staf.keySet()){
                if (staf.get(key).getPosition().equalsIgnoreCase("Топ менеджер")){
                    TopManager topManager = (TopManager) staf.get(key);
                    topManager.setBonus(topManagerBonus);
                    companyMonthlyBalance -= topManagerBonus;
                }
            }
        }

        System.out.println("В прошлом месяце компания заработала " + (companyMonthlyBalance / 100) +
                (companyMonthlyBalance % 100 < 10 ? ",0" + companyMonthlyBalance % 100 : "," + companyMonthlyBalance % 100) + " руб.");
    }

    public void listAllEmployees(){
        for (int key : staf.keySet()){
            System.out.println(staf.get(key).getName() + " - " + staf.get(key).getPosition() + " номер по табелю - " + key);
        }
    }
}
