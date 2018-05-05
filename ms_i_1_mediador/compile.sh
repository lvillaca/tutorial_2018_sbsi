#!/bin/bash
sudo docker rmi -f class_unirio/mediator_microservice
sudo gradle build buildDocker
