package Staff;

public class TopManager extends Operationist {

    private int fixedSalary;
    private int bonus = 0;

    public TopManager(int fixedSalary) {
        super(fixedSalary);
        this.fixedSalary = fixedSalary;
    }

    @Override
    public int getMonthlySalary() {
        return (this.fixedSalary + bonus) * 100;
    }

    @Override
    public String getPosition(){
        return "Топ менеджер";
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
