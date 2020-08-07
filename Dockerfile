FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
EXPOSE 9000
ARG JAR_FILE=target/assignment-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]