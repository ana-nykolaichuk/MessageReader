package com.ing.assignment.messagereader;

import com.ing.assignment.messagereader.logic.MessageReaderServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@Import(MessageReaderServiceConfig.class)
public class MessageReaderApplication {
  public static void main(String[] args) {
    SpringApplication.run(MessageReaderApplication.class, args);
  }
}
