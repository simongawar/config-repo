# syntax=docker/dockerfile:1.4
# Stage 1: Build the layered JAR artifact
# Using Eclipse Temurin 17 JDK (LTS) as it is robust and widely supported.
FROM eclipse-temurin:17-jdk-jammy AS build

# Add Maintainer Info
LABEL maintainer="Simon Gawar g.dakthak@gmail.com"

# Define application arguments
ARG JAR_FILE="target/*.jar"

# Set working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the host build context
# (This assumes the JAR has already been packaged, e.g., using mvn package)
COPY ${JAR_FILE} app.jar

# Run the Spring Boot utility to extract the JAR into layers.
# This creates separate directories (dependencies, snapshot-dependencies, application, etc.)
# which Docker can cache individually.
RUN java -Djarmode=layertools -jar app.jar extract

# Stage 2: Create the final production image
# Using a slim JRE-only base image for a much smaller final footprint
FROM eclipse-temurin:17-jre-jammy

# Add volume pointing to /tmp (Standard practice for Spring Boot temp files)
VOLUME /tmp

# Create a non-root user for security best practices
RUN groupadd --system spring && useradd --system --gid spring spring
USER spring

# Copy extracted layers from the 'build' stage in the specific order
# to leverage Docker's layer caching effectively (least frequently changing first).
# The default layers are: dependencies, snapshot-dependencies, spring-boot-loader, application.
COPY --from=build app/dependencies/ ./
COPY --from=build app/spring-boot-loader/ ./
COPY --from=build app/snapshot-dependencies/ ./
COPY --from=build app/application/ ./

# Expose the default Spring Boot port (optional, but recommended)
EXPOSE 8080

# Execute the application using the Spring Boot JarLauncher
# This is the standard entrypoint when using the layered JAR structure.
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]