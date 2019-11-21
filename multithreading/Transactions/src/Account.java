import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Account
{
    private AtomicLong money;
    private String accNumber;
    private AtomicBoolean isBlocked = new AtomicBoolean();

    public Account(long money, String accNumber) {
        this.money = new AtomicLong();
        this.money.addAndGet(money);
        this.accNumber = accNumber;
        this.isBlocked.set(false);
    }

    public long getMoney() {
        return money.longValue();
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void deposit(long amount){
        if (!isBlocked.get()){
            money.addAndGet(amount);
        }
        else {
            System.out.println("Счёт номер: " + this.accNumber + " заблокирован. \nВсе транзакции запрещены. \nОбратитесь в отделение банка за дополнительной информацией");
        }
    }

    public void withdraw(long amount){
        if (!isBlocked.get()) {
            money.set(money.longValue() - amount);
        }
        else {
            System.out.println("Счёт номер: " + this.accNumber + " заблокирован. \nВсе транзакции запрещены. \nОбратитесь в отделение банка за дополнительной информацией");
        }
    }

    public void blockAccount(){
        isBlocked.set(true);
    }

    public void unBlockAccount(){
        isBlocked.set(false);
    }

    public boolean isBlocked(){
        return isBlocked.get();
    }
}
