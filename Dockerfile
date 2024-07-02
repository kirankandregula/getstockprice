# Use an OpenJDK base image
FROM maven:latest AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven configuration files
COPY pom.xml /app/

# Download dependencies and build application
RUN mvn dependency:go-offline && mvn package

# Second stage: Use the built JAR file
FROM openjdk:latest

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/getstockdetails.jar /app/

# Command to run the application when the container starts
CMD ["java", "-jar", "getstockdetails.jar"]
