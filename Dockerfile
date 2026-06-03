FROM 037941993977.dkr.ecr.eu-west-1.amazonaws.com/image-recognition-service-x85gdcvo:latest
EXPOSE 8080
COPY target/image-service-api-0.0.1-SNAPSHOT.jar image-service-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "image-service-api-0.0.1-SNAPSHOT.jar"]
