#!/bin/bash
sudo docker rmi -f class_unirio/mediator_microservice
sudo ./gradlew.sh build buildDocker

#sudo docker system prune
sudo docker run --name ms04_mediador --rm -p 8084:8080 --network grupo_microsservicos class_unirio/mediator_microservice:latest
