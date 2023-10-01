package com.ing.assignment.messagereader.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ing.assignment.messagereader.api.JavaRocksMessage;
import com.ing.assignment.messagereader.util.XMLValidator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

final class JavaRocksEventConsumer {
  private static final Logger LOG = LoggerFactory.getLogger(JavaRocksEventConsumer.class);
  private static final String JAVA_ROCKS_XSD = "message_schema";

  private final MessageReaderService messageReaderService;
  private final XmlMapper xmlMapper;
  private final XMLValidator xmlValidator;

  JavaRocksEventConsumer(
      MessageReaderService messageReaderService, XmlMapper xmlMapper, XMLValidator xmlValidator) {
    this.messageReaderService = messageReaderService;
    this.xmlMapper = xmlMapper;
    this.xmlValidator = xmlValidator;
  }

  @KafkaListener(topics = "java-rocks-topic")
  void handle(ConsumerRecord<String, String> record) throws JsonProcessingException {
    String xmlMessage = record.value();
    if (!xmlValidator.isValidXml(JAVA_ROCKS_XSD, xmlMessage)) {
      LOG.warn("Invalid XML, skipping the message.");
      return;
    }
    JavaRocksMessage message = xmlMapper.readValue(xmlMessage, JavaRocksMessage.class);
    messageReaderService.processMessage(message);
  }
}
