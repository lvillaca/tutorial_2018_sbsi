#!/bin/bash
sudo docker rmi -f class_unirio/carrocompra_microservice
sudo ./gradlew.sh build buildDocker
#sudo docker system prune
sudo docker run --name ms02_carrocompra --network grupo_microsservicos --rm -p 8082:8080 class_unirio/carrocompra_microservice:latest
