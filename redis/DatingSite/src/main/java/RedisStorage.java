
import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;

public class RedisStorage {
    private RedissonClient redisson;
    private RScoredSortedSet<String> rotationQue;
    private int registeredUsers = 20;

    private final static String KEY = "USERS";

    private double getTs() {
        return new Date().getTime() / 1000;
    }

    void init() throws InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException ex) {
            System.out.println("Не удалось подключиться к Redis");
            System.out.println(ex.getMessage());
        }

        rotationQue = redisson.getScoredSortedSet(KEY);
        for (int i = 0; i < registeredUsers; i++) {
            int user_id = i + 1;
            rotationQue.add(i, String.valueOf(user_id));
            Thread.sleep(200);
        }

        PayingCustomer payingCustomer = new PayingCustomer(this, registeredUsers);
        payingCustomer.start();
    }

    void logServedUser() {
        String user_id = rotationQue.first();// .takeFirst();
        System.out.println("На главной странице показываем пользователя " + user_id);
        moveServedUserToEndOfQue(user_id);
    }

    void moveServedUserToEndOfQue (String user_id) {
        rotationQue.add(getTs(), String.valueOf(user_id));
    }

    void movePayingUserToFirstSpot (int user_id) {
        System.out.println("Пользователь " + user_id + " внес оплату показа");
        rotationQue.add(0, String.valueOf(user_id));
    }
}
