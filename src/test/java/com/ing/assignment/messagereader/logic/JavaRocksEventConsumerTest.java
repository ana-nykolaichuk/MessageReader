package com.ing.assignment.messagereader.logic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ing.assignment.messagereader.api.JavaRocksMessage;
import com.ing.assignment.messagereader.util.XMLValidator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

final class JavaRocksEventConsumerTest {
  private static final JavaRocksMessage MESSAGE =
      new JavaRocksMessage("Jaap@tests.com", "dummy@tests.com", "Reminder", "Java rocks!");
  private static final String XML = "xml";

  @Test
  void handle() throws JsonProcessingException {
    MessageReaderService service = mock();
    XmlMapper xmlMapper = mock();
    XMLValidator xmlValidator = mock();
    ConsumerRecord<String, String> record = mock();
    when(record.value()).thenReturn(XML);
    when(xmlValidator.isValidXml("message_schema", XML)).thenReturn(true);
    when(xmlMapper.readValue(XML, JavaRocksMessage.class)).thenReturn(MESSAGE);

    new JavaRocksEventConsumer(service, xmlMapper, xmlValidator).handle(record);

    verify(service).processMessage(MESSAGE);
  }

  @Test
  void handleInvalidXml() throws JsonProcessingException {
    MessageReaderService service = mock();
    XmlMapper xmlMapper = mock();
    XMLValidator xmlValidator = mock();
    ConsumerRecord<String, String> record = mock();
    when(record.value()).thenReturn(XML);
    when(xmlValidator.isValidXml("schema", XML)).thenReturn(false);

    new JavaRocksEventConsumer(service, xmlMapper, xmlValidator).handle(record);

    verifyNoInteractions(service);
  }
}
