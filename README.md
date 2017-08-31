# order-entry
Consist of 3 microservices:
1) Inventory
2) Catalog
3) Processor

To deploy Inventory, you should set next environment variables:
1) DB_USERNAME
2) DB_PASSWORD
3) DB_HOST
4) DB_PORT

Also, you should create schema called 'order'.

To deploy Catalog, you should set next environment variables:
1) DB_USERNAME
2) DB_PASSWORD
3) DB_HOST
4) DB_PORT

Also, you should create schema called 'offer'.

To deploy processor you should set next environment variables:
1) ORDER_HOST (ip address of Inventory microservice)
2) ORDER_PORT (port of Inventory microservice)
3) OFFER_HOST (ip address of Catalog microservice)
4) OFFER_PORT (port of Catalog microservice)
