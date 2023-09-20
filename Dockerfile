FROM amazoncorretto:17

EXPOSE 8080

VOLUME /tmp

COPY build/libs/omok-0.0.1-SNAPSHOT.jar omok.jar

ENTRYPOINT ["java","-jar","omok.jar"]