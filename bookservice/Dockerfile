#Stage 1: Use Maven for building
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./bookservice ./bookservice
COPY ./commonservice ./commonservice 
#COPY ../commonservice /build/commonservice

WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/bookservice
RUN mvn clean package -DskipTests

#Stage 2: use OpenJDK for running
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/bookservice/target/*.jar bookservice.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","bookservice.jar"]