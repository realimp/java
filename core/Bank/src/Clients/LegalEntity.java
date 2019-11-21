package Clients;

public class LegalEntity extends Individual {
    private int withdrawalCommission = 1;

    public LegalEntity(int initialBalance) {
        super(initialBalance);
    }

    @Override
    public void withdraw(int amount) {
        if ((amount * (100 + withdrawalCommission)) <= balance) {
            balance -= (amount * withdrawalCommission);
            super.withdraw(amount);
        }
        else {
            System.out.println("На Вашем счету недостаточно средств");
            System.out.println("Максимальная сумма доступная для снятия: " + (balance / (100 + withdrawalCommission)) +
                    ((balance % (100 + withdrawalCommission)) < 10 ? ",0" : ",") +
                    (balance % (100 + withdrawalCommission)) + " руб.");
            checkBalance();
        }
    }
}
