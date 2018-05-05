#!/bin/bash
sudo docker rmi -f class_unirio/cadcliente_microservice
sudo ./gradlew.sh build buildDocker
#sudo docker system prune
sudo docker run --name ms03_cadcliente --link mysqldb:mysql_host --rm -p 8083:8080 --network grupo_microsservicos class_unirio/cadcliente_microservice:latest
