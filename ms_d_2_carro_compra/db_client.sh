docker run -it --link redisdb:redishost --rm redis:3.2.11 redis-cli -h redishost -p 6379
