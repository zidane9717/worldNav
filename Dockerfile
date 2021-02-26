FROM maven:3.6.3-jdk-8 AS build-env
WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY . ./
RUN mvn package -DfinalName=worldnav-1.0

FROM openjdk:12-alpine
EXPOSE 8080
WORKDIR /app

COPY --from=build-env /app/target/worldnav-1.0.jar ./worldnav-1.0.jar
CMD ["java", "-jar", "/worldnav-1.0.jar"]
CMD ["/usr/bin/java", "-jar", "/app/worldnav-1.0.jar"]
