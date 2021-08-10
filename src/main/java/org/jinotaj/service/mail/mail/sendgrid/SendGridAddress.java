package org.jinotaj.service.mail.mail.sendgrid;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.jinotaj.service.mail.mail.MailAddress;

/**
 * @author Filip Jirsák
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class SendGridAddress {
  private String email;
  private String name;

  public SendGridAddress() {
  }

  public SendGridAddress(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
