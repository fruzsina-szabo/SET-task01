FROM amazoncorretto:21
EXPOSE 8080
COPY target/image-service-api-0.0.1-SNAPSHOT.jar image-service-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "image-service-api-0.0.1-SNAPSHOT.jar"]