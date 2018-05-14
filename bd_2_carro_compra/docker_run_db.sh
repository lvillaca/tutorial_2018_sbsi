sudo docker run --name redisdb -p 6379:6379 -d --network grupo_microsservicos redis:3.2.11 redis-server --appendonly yes

