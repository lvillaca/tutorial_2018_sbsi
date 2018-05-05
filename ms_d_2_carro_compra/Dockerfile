FROM openjdk:8-jdk-alpine

VOLUME /tmp
#Changing the volume from within the Dockerfile: If any build steps change the data within the volume after
# it has been declared, those changes will be discarded.

ADD target/carrocompra_microservice-0.2.0.jar app.jar

ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
#https://docs.oracle.com/cd/E13209_01/wlcp/wlss30/configwlss/jvmrand.html
#java opts - heap e stack
#Each Java virtual machine thread has a private Java virtual machine stack, created at the same time as the thread.
# A Java virtual machine stack stores frames.
# The Java virtual machine has a heap that is shared among all Java virtual machine threads.
# The heap is the runtime data area from which memory for all class instances and arrays is allocated.
# The heap is created on virtual machine start-up. Heap storage for objects is reclaimed by an automatic
# storage management system (known as a garbage collector); objects are never explicitly deallocated.