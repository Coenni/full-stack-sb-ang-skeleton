FROM maven:3.6.3-adoptopenjdk-11 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package -DskipTests

FROM openjdk:8-alpine
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
