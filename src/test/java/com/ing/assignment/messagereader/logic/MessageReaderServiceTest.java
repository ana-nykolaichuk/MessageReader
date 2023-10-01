package com.ing.assignment.messagereader.logic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.ing.assignment.messagereader.api.JavaRocksMessage;
import org.junit.jupiter.api.Test;

final class MessageReaderServiceTest {
  @Test
  void processMessage() {
    JavaRocksEventProducer producer = mock();
    JavaRocksMessage message =
        new JavaRocksMessage("Jaap@tests.com", "dummy@tests.com", "Reminder", "Java rocks!");
    new MessageReaderService(producer).processMessage(message);

    JavaRocksMessage expectedMessage = message.withBody("JAVA 21 ROCKS!");
    verify(producer).produceMessage(expectedMessage);
  }
}
