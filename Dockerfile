FROM openjdk:11.0.15-slim-buster

WORKDIR /app

COPY ./target/api-rest-customers-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT ["java","-jar","api-rest-customers-0.0.1-SNAPSHOT.jar"]

