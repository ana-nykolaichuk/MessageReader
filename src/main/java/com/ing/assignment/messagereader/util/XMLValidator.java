package com.ing.assignment.messagereader.util;

import java.io.File;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public final class XMLValidator {
  private static final Logger LOG = LoggerFactory.getLogger(XMLValidator.class);

  public boolean isValidXml(String schemaName, String xml) {
    try {
      getValidator(schemaName).validate(new StreamSource(new StringReader(xml)));
      return true;
    } catch (Exception e) {
      LOG.info(e.getMessage());
      return false;
    }
  }

  private Validator getValidator(String schemaName) throws SAXException, URISyntaxException {
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    URI schemaURI =
        Objects.requireNonNull(
                getClass().getClassLoader().getResource(String.format("%s.xsd", schemaName)))
            .toURI();
    Schema schema = schemaFactory.newSchema(new StreamSource(new File(schemaURI)));
    return schema.newValidator();
  }
}
