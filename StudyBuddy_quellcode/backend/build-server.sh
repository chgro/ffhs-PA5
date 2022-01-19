# Start in backend
rm -rf ./src/main/resources/static
cd ../frontend
npm run build-prod
cp -r ./dist/frontend ../backend/src/main/resources/static
cd ../backend
mvn clean package spring-boot:repackage
docker build -t chgrossmann/studienplaner .
docker login
docker push chgrossmann/studienplaner:latest