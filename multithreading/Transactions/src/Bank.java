import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class Bank
{
    private HashMap<String, Account> accounts;
    private final Random random = new Random();
    private ReentrantLock lock = new ReentrantLock();

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) {

        if (amount > 50000){
            lock.lock();

            Account fromAccount = getAccount(fromAccountNum);
            Account toAccount = getAccount(toAccountNum);

            fromAccount.blockAccount();
            toAccount.blockAccount();
            System.out.println("Временная блокировка счетов службой безопасности: " + fromAccountNum + " "  + toAccountNum);

            try {
                if (isFraud(fromAccountNum, toAccountNum, amount)){
                    System.out.println("Счета " + fromAccountNum + " и " + toAccountNum + " заблокированы");
                } else {
                    fromAccount.unBlockAccount();
                    toAccount.unBlockAccount();
                    System.out.println("Счета " + fromAccountNum + " и " + toAccountNum + " разблокированы");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                if (!getAccount(fromAccountNum).isBlocked() && !getAccount(toAccountNum).isBlocked()) {
                    fromAccount.withdraw(amount);
                    toAccount.deposit(amount);
                    System.out.println("Перевод на сумму " + amount + " со счёта " + fromAccountNum + " на счёт " + toAccountNum + " выаолнен успешно.");
                }
            }
        }
        else {
            if (!getAccount(fromAccountNum).isBlocked() && !getAccount(toAccountNum).isBlocked()) {
                getAccount(fromAccountNum).withdraw(amount);
                getAccount(toAccountNum).deposit(amount);
                System.out.println("Перевод на сумму " + amount + " со счёта " + fromAccountNum + " на счёт " + toAccountNum + " выаолнен успешно.");
            }
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum)
    {
        return accounts.get(accountNum).getMoney();
    }

    public void addAccount(String accNumber, long money){
        accounts.put(accNumber, new Account(money, accNumber));
    }

    public Account getAccount(String accountNum){
        return accounts.get(accountNum);
    }
}
