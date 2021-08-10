package org.jinotaj.service.mail.mail.sendgrid;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filip Jirsák
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class SendGridPersonalization {
  private SendGridAddress from;
  private List<SendGridAddress> to = new LinkedList<>();
  private List<SendGridAddress> cc = new LinkedList<>();
  private List<SendGridAddress> bcc = new LinkedList<>();
  private String subject;
//  private Object headers;
//  private Object substitutions;
//  private Object dynamicTemplateData;
//  private Object customArgs;
//  private Integer sendAt;

  public SendGridAddress getFrom() {
    return from;
  }

  public void setFrom(SendGridAddress from) {
    this.from = from;
  }

  public List<SendGridAddress> getTo() {
    return to;
  }

  public void setTo(List<SendGridAddress> to) {
    this.to = to;
  }

  public List<SendGridAddress> getCc() {
    return cc;
  }

  public void setCc(List<SendGridAddress> cc) {
    this.cc = cc;
  }

  public List<SendGridAddress> getBcc() {
    return bcc;
  }

  public void setBcc(List<SendGridAddress> bcc) {
    this.bcc = bcc;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }
}
