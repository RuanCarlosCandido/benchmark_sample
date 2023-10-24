# Use the official Maven image with a specified version
FROM maven:3.8.4-openjdk-11-slim AS build

# Set the working directory in the Docker image
WORKDIR /app

# Copy the pom.xml file and download the dependencies (to improve build speed)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project
COPY src ./src

# Build the project
RUN mvn clean package

# For the final image, use the official OpenJDK image with a specified version
FROM openjdk:11-jre-slim

# Set the working directory in the Docker image
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/uber-SimpleBenchmarkProject-0.0.1-SNAPSHOT.jar .

# Command to run the application
CMD ["java", "-jar", "uber-SimpleBenchmarkProject-0.0.1-SNAPSHOT.jar"]

