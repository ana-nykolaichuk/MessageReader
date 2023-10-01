package com.ing.assignment.messagereader.logic;

import com.ing.assignment.messagereader.api.JavaRocksMessage;

final class MessageReaderService {
  private static final String NEW_BODY = "JAVA 21 ROCKS!";
  private final JavaRocksEventProducer javaRocksEventProducer;

  MessageReaderService(JavaRocksEventProducer javaRocksEventProducer) {
    this.javaRocksEventProducer = javaRocksEventProducer;
  }

  void processMessage(JavaRocksMessage originalMessage) {
    JavaRocksMessage updatedMessage = originalMessage.withBody(NEW_BODY);
    javaRocksEventProducer.produceMessage(updatedMessage);
  }
}
