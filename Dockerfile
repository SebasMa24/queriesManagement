FROM eclipse-temurin:21-jdk-alpine as build

WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV APPLICATION_PORT=8082
EXPOSE $APPLICATION_PORT
CMD ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]
