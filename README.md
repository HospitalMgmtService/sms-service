# SMS service

This microservice is responsible for:

* Handling pushing notifications
* Sending out SMS

## Tech stack

* Build tool: maven >= 3.8.8
* Java: 21
* Framework: Spring boot 3.2.x
* DBMS: MongoDB

## Format code
`mvn spotless:apply`

## JaCoCo report
`mvn clean test jacoco:report`

## SonarQube
* `docker pull sonarqube:lts-community`
* `docker run --name sonar-qube -p 9000:9000 -d sonarqube:lts-community`
* `mvn clean verify sonar:sonar -Dsonar.projectKey=phongvo.sms.master -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_b33a4a7a230bcb98ad783b9ee2b678ae9c8fb404`

## Start application
`mvn spring-boot:run`

## Build application
`mvn clean package`

## Docker guideline
### Build docker image
`docker build -t sms-service:0.9.0 .`

### Tag the image
`docker tag sms-service:0.9 <DOCKER_ACCOUNT>/sms-service:0.9.0`

### Push docker image to Docker Hub
`docker push <DOCKER_ACCOUNT>/sms-service:0.9`

### Run the built image
`docker run -d --name sms-service -p 9190:9190 sms-service:0.9.0`

### Create network
`docker network create pnk-network`

### Start Postgresql in pnk-network
`docker run --network pnk-network --name postgresql -p 5432:5432 -e POSTGRESQL_ROOT_PASSWORD=root -d postgresql:8.0.36-debian`

### Run your application in pnk-network
`docker run --name sms-service --network pnk-network -p 9190:9190 -e DBMS_CONNECTION=jdbc:postgresql://localhost:5432/hospital sms-service:0.9`
