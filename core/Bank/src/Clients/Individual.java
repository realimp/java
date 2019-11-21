package Clients;

public class Individual extends Client {

    public Individual(int initialBalance){
        setBalance(initialBalance);
    }

    @Override
    public void topUpBalance(int amount) {
        balance += (amount * 100);
        System.out.println("Баланс успешно пополнен на " + amount + " руб.");
        checkBalance();
    }

    @Override
    public void withdraw(int amount) {
        if ((amount * 100) <= balance) {
            balance -= (amount * 100);
            System.out.println("Вы успешно сняли " + amount + " руб.");
        }
        else {
            System.out.println("На Вашем счету недостаточно средств");
        }
        checkBalance();
    }
}
