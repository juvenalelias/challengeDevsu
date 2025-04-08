# Use an OpenJDK image as the base image
FROM openjdk:17-jdk-slim  AS build

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa final
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/cliente-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

