FROM amazoncorretto:17-alpine
WORKDIR /app
COPY app.jar app.jar
RUN mkdir -p /app/logs
EXPOSE 8080
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "-Dspring.profiles.active=prod", "app.jar"]