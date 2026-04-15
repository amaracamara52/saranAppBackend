## main-project/clinic-app/backend/Dockerfile
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY target/*.jar ./app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]


#FROM openjdk:17-jdk-slim
#VOLUME /tmp
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#EXPOSE 8080



#FROM openjdk:17-jdk-slim AS builder
#WORKDIR /
#COPY pom.xml .
#COPY src ./src
#RUN apt-get update && apt-get install -y maven
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17-jdk-slim
#VOLUME /tmp
#COPY --from=builder /target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

# Dockerfile optimisé pour CapRover et Spring Boot
# Stage 1: Build de l'application


FROM maven:3-openjdk-17-slim AS builder

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port (adjust if your app uses different port)
EXPOSE 8091

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


#ssh -t root@87.106.171.59 "bash --noprofile --norc"
#xXzalLo8