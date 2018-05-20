FROM openjdk:8-jdk-alpine

ADD target/catalogoprod_microservice-0.1.0.jar app.jar

ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar /app.jar