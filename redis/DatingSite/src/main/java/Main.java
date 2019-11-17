public class Main {
    private static int users = 20;
    private static RedisStorage redis;

    public static void main(String[] args) throws InterruptedException {

        redis = new RedisStorage();
        redis.init();
        while (true) {
            for(int request = 0; request < users; request++) {
                redis.logServedUser();
                Thread.sleep(500);
            }
            Thread.sleep(1000);
        }
    }
}
