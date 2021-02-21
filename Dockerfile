FROM openjdk:12-alpine
COPY target/worldnav-1.0.jar /worldnav-1.0.jar
CMD ["java" , "-jar" , "/worldnav-1.0.jar"]