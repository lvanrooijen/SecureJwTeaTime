FROM openjdk:17-jdk-alpine
LABEL authors="laila"

ARG JAR_FILE=target/*.jar

COPY target/SecureJwTeaTime-0.0.1-SNAPSHOT.jar SecureJwTeaTime.jar

ENTRYPOINT ["java", "-jar","/SecureJwTeaTime.jar"]