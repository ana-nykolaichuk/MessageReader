package com.ing.assignment.messagereader.logic;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ing.assignment.messagereader.util.XMLValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({
  KafkaConsumerConfig.class,
  KafkaProducerConfig.class,
  MessageReaderService.class,
  XMLValidator.class
})
public class MessageReaderServiceConfig {
  @Bean
  public XmlMapper javaRocksXmlMapper() {
    return new XmlMapper();
  }
}
