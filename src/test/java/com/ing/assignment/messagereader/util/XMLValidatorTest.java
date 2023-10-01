package com.ing.assignment.messagereader.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class XMLValidatorTest {
  @Test
  void isValid() {
    XMLValidator xmlValidator = new XMLValidator();
    String xml =
        """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <note>
                      <from>Jaap@tests.com</from>
                      <to>dummy@tests.com</to>
                      <heading>Reminder</heading>
                      <body>Java rocks!</body>
                    </note>""";
    boolean result = xmlValidator.isValidXml("message_schema", xml);
    assertTrue(result);
  }

  @Test
  void isValidTooLongHeader() {
    XMLValidator xmlValidator = new XMLValidator();
    String xml =
        """
                        <?xml version="1.0" encoding="UTF-8"?>
                        <note>
                          <from>Jaap@tests.com</from>
                          <to>dummy@tests.com</to>
                          <heading>ReminderReminderReminder</heading>
                          <body>Java rocks!</body>
                        </note>""";
    boolean result = xmlValidator.isValidXml("message_schema", xml);
    assertFalse(result);
  }

  @Test
  void isValidWrongSequence() {
    XMLValidator xmlValidator = new XMLValidator();
    String xml =
        """
                        <?xml version="1.0" encoding="UTF-8"?>
                        <note>
                          <from>Jaap@tests.com</from>
                          <heading>Reminder</heading>
                          <to>dummy@tests.com</to>
                          <body>Java rocks!</body>
                        </note>""";
    boolean result = xmlValidator.isValidXml("message_schema", xml);
    assertFalse(result);
  }

  @Test
  void isValidMissingElement() {
    XMLValidator xmlValidator = new XMLValidator();
    String xml =
        """
                        <?xml version="1.0" encoding="UTF-8"?>
                        <note>
                          <from>Jaap@tests.com</from>
                          <to>dummy@tests.com</to>
                          <body>Java rocks!</body>
                        </note>""";
    boolean result = xmlValidator.isValidXml("message_schema", xml);
    assertFalse(result);
  }
}
