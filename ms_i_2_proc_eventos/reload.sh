#!/bin/bash
sudo docker rmi -f class_unirio/proc_eventos_microservice
sudo ./gradlew.sh build buildDocker
#sudo docker system prune
sudo docker run --name ms_proc_eventos --network grupo_microsservicos --rm -p 8085:8080 class_unirio/proc_eventos_microservice:latest
