FROM java:8
EXPOSE 8082
ADD target/processor-1.0-SNAPSHOT.jar processor.jar
ENTRYPOINT ["java", "-jar", "processor.jar"]