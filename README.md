requirements:
----------------
* Java 17
* Docker
* Maven


how to run:
---------------
run these commands in project directory:
* mvn clean install
* docker-compose build
* docker-compose up -d

import postman endpoints and run

scan domain: 
http://localhost:8080/api/v1/harvester/scan/{domain}

list domain scan history: 
http://localhost:8080/api/v1/harvester/records/{domain}

