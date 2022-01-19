FROM openjdk:16
VOLUME /tmp
EXPOSE 8080
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ADD target/backend-1.0.0.jar backend.jar
ENTRYPOINT ["./wait-for-it.sh","db:3306","--","java","-jar","backend.jar"]