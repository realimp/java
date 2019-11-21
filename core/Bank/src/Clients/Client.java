package Clients;

public abstract class Client {
    protected int balance;

    public abstract void topUpBalance(int amount);

    public abstract void withdraw(int amount);

    public void checkBalance(){
        System.out.println("Ваш баланс: " + (balance / 100) +
                ((balance % 100) < 10 ? ",0" : ",") + (balance % 100) + " руб.");
    }

    public int getBalance() {
        return balance;
    }

    protected void setBalance(int balance) {
        this.balance = balance * 100;
    }
}
