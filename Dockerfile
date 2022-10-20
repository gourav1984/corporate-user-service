FROM openjdk:8-apline

WORKDIR /opt

RUN mkdir config && chmod 777 config \
    && mkdir logs && chmod 777 logs

COPY target/ corporate-user-service-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources config

EXPOSE 8080
VOLUME /opt/logs

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]