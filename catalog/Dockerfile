FROM java:8
EXPOSE 8081
ADD /target/catalog-1.0-SNAPSHOT.jar catalog.jar
ENTRYPOINT ["java", "-jar", "catalog.jar"]