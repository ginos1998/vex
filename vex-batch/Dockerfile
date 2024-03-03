FROM maven:3.8-amazoncorretto-17 AS build
LABEL authors="ginos"

WORKDIR /app
COPY pom.xml .

RUN mvn dependency:go-offline -B
COPY src ./src

RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar batch.jar

CMD ["java", "-jar", "batch.jar"]