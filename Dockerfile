FROM openjdk:12-alpine
ADD target/worldnav-1.0.jar /worldnav-1.0.jar
ENTRYPOINT ["java" , "-jar" , "/worldnav-1.0.jar"]
