FROM openjdk:12-alpine
RUN addgroup -S spring && adduser -S spring -G spring
ARG BUILD_DIR=build
COPY ${BUILD_DIR}/libs /app/lib/
CMD ["java" , "-jar" , "/worldnav-1.0.jar"]