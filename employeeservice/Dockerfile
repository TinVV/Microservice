#Stage 1: Use Maven for building
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY ./employeeservice ./employeeservice
COPY ./commonservice ./commonservice 

WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/employeeservice
RUN  mvn clean package -DskipTests

#Stage 2: use OpenJDK for running
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/employeeservice/target/*.jar employeeservice.jar
EXPOSE 9002
ENTRYPOINT ["java","-jar","employeeservice.jar"]