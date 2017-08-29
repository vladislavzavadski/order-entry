FROM java:8
EXPOSE 8080
ADD target/inventory-1.0-SNAPSHOT.jar inventory.jar
ENTRYPOINT ["java", "-jar", "inventory.jar"]