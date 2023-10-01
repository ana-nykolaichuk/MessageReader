package com.ing.assignment.messagereader.logic;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ing.assignment.messagereader.api.JavaRocksMessage;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = MessageReaderServiceConfig.class)
@EnableKafka
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    topics = {"java-rocks-topic", "java-21-rocks-topic"})
final class MessageReaderServiceIntegrationTest {
  @Autowired private EmbeddedKafkaBroker embeddedKafkaBroker;

  @Test
  public void processMessage() {
    String xml =
        """
        <?xml version="1.0" encoding="UTF-8"?>
        <note>
          <from>Jaap@tests.com</from>
          <to>dummy@tests.com</to>
          <heading>Reminder</heading>
          <body>Java rocks!</body>
        </note>
        """;

    Consumer<Integer, JavaRocksMessage> consumer = configureConsumer();
    Producer<Integer, String> producer = configureProducer();

    producer.send(new ProducerRecord<>("java-rocks-topic", 0, xml));

    JavaRocksMessage updatedMessage =
        KafkaTestUtils.getSingleRecord(consumer, "java-21-rocks-topic").value();
    assertThat(updatedMessage)
        .isEqualTo(
            new JavaRocksMessage(
                "Jaap@tests.com", "dummy@tests.com", "Reminder", "JAVA 21 ROCKS!"));

    consumer.close();
    producer.close();
  }

  @Test
  public void processMessageInvalidInput() {
    String xml =
        """
            <?xml version="1.0" encoding="UTF-8"?>
            <invalid>
              <from>Jaap@tests.com</from>
              <to>dummy@tests.com</to>
              <heading>Reminder</heading>
              <body>Java doesn't rock!</body>
            </invalid>
            """;

    Consumer<Integer, JavaRocksMessage> consumer = configureConsumer();
    Producer<Integer, String> producer = configureProducer();

    producer.send(new ProducerRecord<>("java-rocks-topic", 0, xml));

    assertThrows(
        IllegalStateException.class,
        () ->
            KafkaTestUtils.getSingleRecord(
                consumer, "java-21-rocks-topic", Duration.of(3, SECONDS)));

    consumer.close();
    producer.close();
  }

  private Consumer<Integer, JavaRocksMessage> configureConsumer() {
    Map<String, Object> consumerProps =
        KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    Consumer<Integer, JavaRocksMessage> consumer =
        new DefaultKafkaConsumerFactory<Integer, JavaRocksMessage>(consumerProps).createConsumer();
    consumer.subscribe(Collections.singleton("java-21-rocks-topic"));
    return consumer;
  }

  private Producer<Integer, String> configureProducer() {
    Map<String, Object> producerProps =
        new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
    return new DefaultKafkaProducerFactory<Integer, String>(producerProps).createProducer();
  }
}
