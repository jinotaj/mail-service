package org.jinotaj.service.mail.mail.sendgrid;

import org.jinotaj.service.mail.mail.Attachment;
import org.jinotaj.service.mail.mail.Content;
import org.jinotaj.service.mail.mail.MailAddress;
import org.jinotaj.service.mail.mail.Message;

import javax.inject.Singleton;
import java.util.Base64;

/**
 * @author Filip Jirsák
 */
@Singleton
public class SendGridService {
  private final SendGridClient client;
  private final Base64.Encoder base64Encoder = Base64.getMimeEncoder();

  public SendGridService(SendGridClient client) {
    this.client = client;
  }

  public void send(Message message) {
    SendGridMessage sendGridMessage = toSendGrid(message);
    client.sendMail(sendGridMessage);
  }

  private SendGridMessage toSendGrid(Message message) {
    SendGridMessage sendGridMessage = new SendGridMessage();
    sendGridMessage.setSubject(message.getSubject());
    sendGridMessage.setFrom(toSendGrid(message.getFrom()));

    message.getContent()
            .stream()
            .map(this::toSendGrid)
            .forEach(content -> sendGridMessage.getContent().add(content));

    message.getAttachments()
            .stream()
            .map(this::toSendGrid)
            .forEach(attachment -> sendGridMessage.getAttchments().add(attachment));

    SendGridPersonalization personalization = new SendGridPersonalization();
    personalization.getTo().add(toSendGrid(message.getFrom()));
    sendGridMessage.getPersonalizations().add(personalization);
    return sendGridMessage;
  }

  private SendGridAddress toSendGrid(MailAddress mailAddress) {
    return new SendGridAddress(mailAddress.getEmail(), mailAddress.getName());
  }

  private SendGridContent toSendGrid(Content content) {
    return new SendGridContent(content.getType(), content.getValue());
  }

  private SendGridAttachment toSendGrid(Attachment attachment) {
    SendGridAttachment sendGridAttachment = new SendGridAttachment();
    sendGridAttachment.setType(attachment.getType());
    sendGridAttachment.setFilename(attachment.getFilename());
    sendGridAttachment.setDisposition(attachment.getDisposition());
    sendGridAttachment.setContentId(attachment.getId());
    sendGridAttachment.setContent(base64Encoder.encodeToString(attachment.getContent()));
    return sendGridAttachment;
  }
}
