#!/bin/bash
sudo docker rmi -f class_unirio/catalogoprod_microservice
sudo ./gradlew.sh build buildDocker
#sudo docker system prune
sudo docker run --name ms01_catalogoprod --network grupo_microsservicos --rm -p 8081:8080 class_unirio/catalogoprod_microservice:latest
