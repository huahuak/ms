# syntax=docker/dockerfile:1
FROM openjdk:8
COPY target/backend-0.0.1-SNAPSHOT.jar /usr/src/ms/target/backend-0.0.1-SNAPSHOT.jar
WORKDIR /usr/src/ms
EXPOSE 8090 
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=docker", "target/backend-0.0.1-SNAPSHOT.jar" ]
