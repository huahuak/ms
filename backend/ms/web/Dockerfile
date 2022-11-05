# syntax=docker/dockerfile:1
FROM openjdk:8
COPY target/web-0.0.1-SNAPSHOT.jar /usr/src/ms/target/web-0.0.1-SNAPSHOT.jar
WORKDIR /usr/src/ms
EXPOSE 8083 
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=docker", "target/web-0.0.1-SNAPSHOT.jar" ]
