FROM adoptopenjdk/openjdk12:latest
EXPOSE 8080
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} berrygood.jar
ENTRYPOINT ["java", "-jar", "/berrygood.jar"]