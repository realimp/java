## Background
We'll be using Redis to emulate rotating banner on the front page of a dating website. Let's assume that we have a Dating Site with 20 registered users. Every user is displayed on front page one by one ordered by their registration date. After each user is displayed it goes to the end of queue. Any user can order a payed display which puts him next in queue.

## The task
Our program should store users queue in Redis and:
1. start endless cycle logging to console info about which user is shown on front page.
2. on every 10th iteration of a cycle random user pays for a privileged display and his id is logged to console
3. wait for a second after cycle ends and restart the cycle

Sample output:

+ Showing user 1
+ Showing user 2
+ Showing user 3
+ User 8 payed for privileged display
+ Showing user 8
+ Showing user 4
+ Showing user 5
+ ...
+ Showing user 1
+ ...

I'll be using docker to test the program
#### Install Docker for Windows
https://docs.docker.com/docker-for-windows/install/
#### Install Docker for Mac
https://docs.docker.com/docker-for-mac/install/

#### Install Redis
```
docker pull redis
```
#### Start docker container
```
docker run --rm --name some-redis -p 127.0.0.1:6379:6379/tcp -d redis
```

## The Solution
We're going to use Redisson Java client to interact with Redis so lets add a maven dependency
#### pom.xml

```xml
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson</artifactId>
    <version>3.11.4</version>
</dependency>
```
Next we'll create RedisStorage class responsible for all redis interactions which will have the fields below.
#### RedisStorage.java
```java
private RedissonClient redisson; //instance of Redisson Client
private RScoredSortedSet<String> rotationQueue; //set to store our queue
private int registeredUsers = 20; //number of registered users
private final static String KEY = "USERS"; //Redis key to get users

// Initializing RedisStorage class by
// configuring and initializing Redis connection
// getting rotation queue
// and emulating paying customer
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

// this method gets the first user in queue
// logs him to console and moves him to the end of queue
// using sort of a timestamp as RScoredSortedSet<String> score
void logServedUser() {
    String user_id = rotationQueue.first();
    System.out.println("Showing user " + user_id);
    rotationQueue.add(new Date().getTime() / 1000, user_id);
}

// this method moves paying customer to the start of queue
// and logs it console
void movePayingUserToFirstSpot (int user_id) {
    System.out.println("User " + user_id + " payed for privileged display");
    rotationQueue.add(0, String.valueOf(user_id));
}
```
Now we need our paying customer emulation. For this I'll be using additional class that extends Thread interface with simple run() method that gets a random user id and moves that user up the queue.
#### PayingCustomer.java
```java
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
```
Finally we just need to initialize RedisStorage class from our Main class and start the endless cycle to show users.
#### Main.java
```java
redis = new RedisStorage();
redis.init();
while (true) {
    for (int request = 0; request < users; request++) {
        redis.logServedUser();
        Thread.sleep(500);
    }
    Thread.sleep(1000);
}
```