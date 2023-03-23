FROM amazoncorretto:17-alpine
COPY target/elevator-service-0.0.1.jar elevator-service.jar
ENTRYPOINT ["java","-jar","/elevator-service.jar"]
EXPOSE 8082
