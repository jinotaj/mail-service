package org.jinotaj.service.mail.mail.sendgrid;

import com.fasterxml.jackson.databind.SerializationFeature;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.jackson.annotation.JacksonFeatures;

/**
 * @author Filip Jirsák
 */
@Client("sendgrid")
@Header(name = "Authorization", value = "Bearer ${mail-service.repositories.default.api-key}")
public interface SendGridClient {
  @Post("mail/send")
  void sendMail(@Body SendGridMessage message);
}
