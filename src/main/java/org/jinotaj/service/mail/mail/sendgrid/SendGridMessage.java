package org.jinotaj.service.mail.mail.sendgrid;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filip Jirsák
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
class SendGridMessage {
  private List<SendGridPersonalization> personalizations = new LinkedList<>();
  private SendGridAddress from;
  private String subject;
  private List<SendGridContent> content = new LinkedList<>();
  private List<SendGridAttachment> attachments = new LinkedList<>();
  private String templateId;
  //  private String headers;
  //  private String categories;
  private String customArgs;
  //  private Integer sendAt;
  private String batchId;

  public List<SendGridPersonalization> getPersonalizations() {
    return personalizations;
  }

  public void setPersonalizations(List<SendGridPersonalization> personalizations) {
    this.personalizations = personalizations;
  }

  public SendGridAddress getFrom() {
    return from;
  }

  public void setFrom(SendGridAddress from) {
    this.from = from;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public List<SendGridContent> getContent() {
    return content;
  }

  public void setContent(List<SendGridContent> content) {
    this.content = content;
  }

  public List<SendGridAttachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<SendGridAttachment> attachments) {
    this.attachments = attachments;
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public String getCustomArgs() {
    return customArgs;
  }

  public void setCustomArgs(String customArgs) {
    this.customArgs = customArgs;
  }

  public String getBatchId() {
    return batchId;
  }

  public void setBatchId(String batchId) {
    this.batchId = batchId;
  }
}
