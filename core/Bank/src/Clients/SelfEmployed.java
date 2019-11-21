package Clients;

public class SelfEmployed extends Individual {
    private int topUpCommissionThreshold = 1000;
    private int topUpCommissionBelowThreshold = 10; // 1%
    private int topUpCommissionAboveThreshold = 5; // 0.5%

    public SelfEmployed(int initialBalance) {
        super(initialBalance);
    }

    @Override
    public void topUpBalance(int amount) {
        int topUpCommission = (amount < topUpCommissionThreshold) ?
                (amount * topUpCommissionBelowThreshold) : (amount * topUpCommissionAboveThreshold);
        topUpCommission = (topUpCommission % 10) > 0 ? (topUpCommission / 10 + 1) : (topUpCommission / 10);
        System.out.println("Коммисия за пополнение: " + (topUpCommission / 100) +
                (topUpCommission % 100 < 10 ? ",0" : ",") + (topUpCommission % 100) + " руб.");
        balance -= topUpCommission;
        super.topUpBalance(amount);
    }
}
