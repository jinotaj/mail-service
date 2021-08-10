package org.jinotaj.service.mail.mail.sendgrid;

import org.jinotaj.service.mail.mail.Content;
import org.jinotaj.service.mail.mail.MailAddress;
import org.jinotaj.service.mail.mail.Message;

import javax.inject.Singleton;

/**
 * @author Filip Jirsák
 */
@Singleton
public class SendGridService {
  private final SendGridClient client;

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
}
