## Background
We'll be using Redis to emulate rotating banner on the front page of a dating website. Let's assume that we have a Dating Site with 20 registered users. Every user is displayed on front page one by one ordered by their registration date. After each user is displayed it goes to the end of queue. Any user can order a payed display which puts him next in queue.

## The task
Our program should store users queue in Redis and:
1. start endless cycle logging to console info about which user is shown on front page.
2. on every 10th iteration of a cycle random user pays for a privileged display and his id is logged to console
3. wait for a second after each cycle iteration

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