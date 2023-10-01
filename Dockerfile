FROM openjdk:17-oracle

WORKDIR /app

COPY target/MessageReader-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "MessageReader-0.0.1-SNAPSHOT.jar"]
