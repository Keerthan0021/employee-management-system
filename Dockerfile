# Base image with Java 17 JRE (lightweight Alpine Linux)
FROM eclipse-temurin:17-jre-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from target folder into the container
COPY target/*.jar app.jar

# Expose port 8080 so external requests can reach it
EXPOSE 8080

# Command to execute when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]


# Change Java 17 to Java 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]