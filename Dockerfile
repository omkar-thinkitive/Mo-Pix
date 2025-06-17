FROM openjdk:17
ENV SPRING_PROFILES_ACTIVE=dev
COPY target/Mopix-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]