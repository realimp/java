package Staff;

public class SalesManager extends Operationist {

    private int fixedSalary;
    private int bonusPercent = 5;
    private int monthlyEarnings;

    public SalesManager(int fixedSalary) {
        super(fixedSalary);
        this.fixedSalary = fixedSalary;
    }

    @Override
    public int getMonthlySalary() {
        int salary = (fixedSalary * 100) + (monthlyEarnings * bonusPercent);

        return salary;
    }

    @Override
    public String getPosition(){
        return "Менеджер по продажам";
    }

    public int earnMoney(){
        this.monthlyEarnings = (int) (Math.random() * 200000);
        return monthlyEarnings;
    }
}
