**NEU**
1. JAR erzeugen (Backend-Verzeichnis): mvn install -DskipTests
1. Docker Build: docker compose build --no-cache
1. Docker starten: docker compose up


Swagger URl
- http://localhost:8080/api.html

MYSQL Befehle

- Verbinden: docker-compose exec db  bash
- login: mysql -u root -p
- Passwort ist 1234
- show databases;
- use studyplaner;
- show tables;
- select * from module;


_ALT: MYSQL Docker_

- Mysql-Image installieren: docker pull mysql
- Docker Image (docker-compose.yml): docker-compose up
- Docker Image entfernen: docker-compose rm
