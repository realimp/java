
import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;

public class RedisStorage {
    private RedissonClient redisson;
    private RScoredSortedSet<String> rotationQueue;
    private int registeredUsers = 20;
    private final static String KEY = "USERS";

    void init() throws InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException ex) {
            System.out.println("Unable to connect to Redis");
            System.out.println(ex.getMessage());
        }

        rotationQueue = redisson.getScoredSortedSet(KEY);
        for (int i = 0; i < registeredUsers; i++) {
            int user_id = i + 1;
            rotationQueue.add(i, String.valueOf(user_id));
        }

        PayingCustomer payingCustomer = new PayingCustomer(this, registeredUsers);
        payingCustomer.start();
    }

    void logServedUser() {
        String user_id = rotationQueue.first();
        System.out.println("Showing user " + user_id);
        rotationQueue.add(new Date().getTime() / 1000, user_id);
    }

    void movePayingUserToFirstSpot (int user_id) {
        System.out.println("User " + user_id + " payed for privileged display");
        rotationQueue.add(0, String.valueOf(user_id));
    }
}
