public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        for (int i = 1000000; i < 1020000; i++){
            bank.addAccount(String.valueOf(i), (long)(Math.random() * 1000000));
        }

        for (int i = 1010000; i > 1008800; i--){
            int y = i + 9999;

            long transferAmount = (long) (Math.random() * 52000);
            Account fromAccount = bank.getAccount(String.valueOf(i));
            Account toAccount = bank.getAccount(String.valueOf(y));

            new Thread(() -> {
                if (bank.getBalance(fromAccount.getAccNumber()) < transferAmount){
                    System.out.println("На счету не достаточно средств.\n" + "Доступный остаток - " + bank.getBalance(fromAccount.getAccNumber()));
                }
                else {
                    bank.transfer(fromAccount.getAccNumber(), toAccount.getAccNumber(), transferAmount);
                }
            }).start();

            if (transferAmount > 50000){
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bank.transfer(fromAccount.getAccNumber(), toAccount.getAccNumber(), transferAmount / 2);
                }).start();
            }
        }
    }
}
