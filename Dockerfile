# Use an appropriate base image with Java 11
FROM openjdk:11

# Set the working directory
WORKDIR /app

# Copy the JAR file from the project directory to the container
COPY ./build/libs/book-store-0.0.1-SNAPSHOT.jar book-store.jar

# Define the command to run the application
CMD ["java", "-jar", "book-store.jar"]
