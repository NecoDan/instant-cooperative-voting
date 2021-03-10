#
# Package stage
#
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ARG JAR_FILE
ADD target/instant-cooperative-voting-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=local", "-jar", "/app/app.jar"]