package Accounts;

public class CurrentAccount {
    protected int balance;

    public CurrentAccount(int balance){
        setBalance(balance);
    }

    public int getBalance(){
        return balance;
    }

    private void setBalance(int balance){
        this.balance = balance * 100;
    }

    public void topUpAccount(int amount){
        balance += amount * 100;
        System.out.println("You've successfully topped up your account by " + amount + "!");
        checkBalance();
    }

    public void withdraw(int amount){
        if (amount * 100 <= balance) {
            balance -= amount * 100;
            System.out.println("You've successfully withdrawn " + amount + " from your account!");
        }
        else {
            System.out.println("Insufficient funds!");
        }
        checkBalance();
    }

    public void checkBalance(){
        System.out.println("Your balance is - " + (balance / 100) +
                ((balance % 100) < 10 ? ",0" : ",") + (balance % 100));
    }
}
