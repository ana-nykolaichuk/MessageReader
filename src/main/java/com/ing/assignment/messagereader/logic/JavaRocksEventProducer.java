package com.ing.assignment.messagereader.logic;

import com.ing.assignment.messagereader.api.JavaRocksMessage;
import org.springframework.kafka.core.KafkaTemplate;

final class JavaRocksEventProducer {
  private static final String TOPIC = "java-21-rocks-topic";
  private final KafkaTemplate<String, JavaRocksMessage> template;

  public JavaRocksEventProducer(KafkaTemplate<String, JavaRocksMessage> template) {
    this.template = template;
  }

  public void produceMessage(JavaRocksMessage message) {
    template.send(TOPIC, message);
  }
}
