package com.ing.assignment.messagereader.logic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.ing.assignment.messagereader.api.JavaRocksMessage;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

final class JavaRocksEventProducerTest {
  @Test
  void produceMessage() {
    KafkaTemplate<String, JavaRocksMessage> template = mock();
    JavaRocksMessage message =
        new JavaRocksMessage("Jaap@tests.com", "dummy@tests.com", "Reminder", "Java rocks!");
    new JavaRocksEventProducer(template).produceMessage(message);

    verify(template).send("java-21-rocks-topic", message);
  }
}
