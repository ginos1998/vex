FROM maven:3.8-amazoncorretto-17 AS build
LABEL authors="ginos"

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/*.jar api.jar

CMD ["java", "-jar", "api.jar"]