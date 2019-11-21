package Accounts;

public class CardAccount extends CurrentAccount {

    private static int commission = 1;

    public CardAccount(int balance) {
        super(balance);
    }

    public void withdraw(int amount) {
        if ((amount * (100 + commission)) <= balance){
            balance -= (amount * commission);
            super.withdraw(amount);
        }
        else {
            System.out.println("Insufficient funds!");
        }
    }

    public static int getCommission() {
        return commission;
    }

    public static void setCommission(int commission) {
        CardAccount.commission = commission;
    }
}
