FROM java:8
EXPOSE 8081
ADD /target/order-catalog-1.0-SNAPSHOT.jar offer.jar
ENTRYPOINT ["java", "-jar", "offer.jar"]