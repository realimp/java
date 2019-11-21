package Accounts;

import java.util.Calendar;
import java.util.Date;

public class DebitAccount extends CurrentAccount {

    private Date lastTopUpDate;

    public DebitAccount(int balance) {
        super(balance);
    }

    public void topUpAccount(int amount){
        super.topUpAccount(amount);
        lastTopUpDate = new Date();
    }

    public void withdraw(int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastTopUpDate);
        calendar.add(Calendar.MONTH, 1);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());

        if (calendar1.after(calendar)){
            super.withdraw(amount);
        }
        else {
            System.out.println("You can not withdraw funds from your account yet!");
        }
    }

    public Date getLastTopUpDate() {
        return lastTopUpDate;
    }
}
