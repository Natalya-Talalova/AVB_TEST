FROM openjdk:17-jdk-slim
LABEL authors="sunen"
WORKDIR /app
COPY target/gateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]