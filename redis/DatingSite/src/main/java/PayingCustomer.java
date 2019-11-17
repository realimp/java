import java.util.Random;

public class PayingCustomer extends Thread {

    private RedisStorage redis;
    private int users;

    public PayingCustomer(RedisStorage redis, int users) {
        this.redis = redis;
        this.users = users;
    }

    public void run(){
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int user_id = new Random().nextInt(users);
            if (user_id == 0) user_id = 1;
            redis.movePayingUserToFirstSpot(user_id);
        }
    }

}
