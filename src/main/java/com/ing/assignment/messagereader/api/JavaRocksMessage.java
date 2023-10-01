package com.ing.assignment.messagereader.api;

public record JavaRocksMessage(String from, String to, String heading, String body) {
  public JavaRocksMessage withBody(String newBody) {
    return new JavaRocksMessage(from, to, heading, newBody);
  }
}
