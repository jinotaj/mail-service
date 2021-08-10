package org.jinotaj.service.mail.mail.sendgrid;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author Filip Jirsák
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class SendGridContent {
  private String type;
  private String value;

  public SendGridContent() {
  }

  public SendGridContent(String type, String content) {
    this.type = type;
    this.value = content;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
