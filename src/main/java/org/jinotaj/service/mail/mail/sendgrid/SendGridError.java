package org.jinotaj.service.mail.mail.sendgrid;

import io.micronaut.core.annotation.Introspected;

import java.util.Map;

/**
 * @author Filip Jirsák
 */
@Introspected
public class SendGridError {
  private String message;
  private String field;
  private Map<String, Object> help;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public Map<String, Object> getHelp() {
    return help;
  }

  public void setHelp(Map<String, Object> help) {
    this.help = help;
  }
}
