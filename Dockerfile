FROM java:8
VOLUME /tmp
EXPOSE 8080

COPY build/libs/*.jar app.jar
COPY wait-for-it.sh .