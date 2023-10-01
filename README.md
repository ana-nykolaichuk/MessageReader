# Java Rocks Message Listener
The application:
- listens to Kafka topic `java-rocks-topic` 
- validates the XML against the defined XSD schema
- transforms the message by updating the body
- publishes the message to the Kafka topic `java-21-rocks-topic`

### Guides
To run the build with all the tests, run `mvn -T 1.0C clean install`

Alternatively, you can run the application on your docker:

Run `docker compose build; docker compose up` to run the application.

To publish the message, enter the Kafka image shell by running `docker exec -it <image ID> /bin/bash`.

When inside the image, you can produce the message by executing `kafka-console-producer --topic java-rocks-topic --bootstrap-server localhost:9092`

You can consume transformed message by executing `kafka-console-consumer --topic java-21-rocks-topic --from-beginning --bootstrap-server localhost:9092`
