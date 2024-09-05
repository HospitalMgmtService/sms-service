# SMS service

This microservice is responsible for:

* Handling pushing notifications
* Sending out SMS

## Tech stack

* Build tool: maven >= 3.8.8
* Java: 21
* Framework: Spring boot 3.2.x
* DBMS: MongoDB

## Executions

### Install Mongodb from Docker Hub
`docker pull bitnami/mongodb:7.0.12`

### Start Mongodb server at port 27017 with root username and password: root/root
`docker run -d --name mongodb-7.0.12 -p 27017:27017 -e MONGODB_ROOT_USER=root -e MONGODB_ROOT_PASSWORD=root bitnami/mongodb:7.0.12`

### Install Kafka
`PS C:\SpringBootProjects\HospitalMgmtService> docker compose up -d`