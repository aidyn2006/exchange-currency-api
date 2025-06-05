FROM maven:latest AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:latest


COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

