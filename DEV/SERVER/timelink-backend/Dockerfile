#FROM ubuntu:latest
#LABEL authors="andre"
#
#ENTRYPOINT ["top", "-b"]

# ====== 1.Building the app ======
FROM eclipse-temurin:17-jdk AS build

WORKDIR ./

# Copy Maven descriptor first to leverage Docker cache
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies (cached if pom.xml does not change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests -Dmaven.test.skip=true

# ====== 2) Runtime stage ======
FROM eclipse-temurin:17-jre AS runtime

WORKDIR ./

# Copy the built jar from the build stage
COPY --from=build /target/*.jar app.jar

# Expose port
EXPOSE 5996

# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]
