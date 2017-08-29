FROM java:8
EXPOSE 8080
ADD target/order-1.0-SNAPSHOT.jar order.jar
ENTRYPOINT ["java", "-jar", "order.jar"]