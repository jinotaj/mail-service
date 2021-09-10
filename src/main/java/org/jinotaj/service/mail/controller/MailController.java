package org.jinotaj.service.mail.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import org.graalvm.polyglot.Value;
import org.jinotaj.service.mail.mail.MailAddress;
import org.jinotaj.service.mail.mail.Message;
import org.jinotaj.service.mail.mail.sendgrid.SendGridService;
import org.jinotaj.service.mail.script.ScriptService;

/**
 * @author Filip Jirsák
 */
@Controller
public class MailController {
  private final ScriptService scriptService;
  private final SendGridService sendGridService;

  public MailController(ScriptService scriptService, SendGridService sendGridService) {
    this.scriptService = scriptService;
    this.sendGridService = sendGridService;
  }

  @Post("/{+path}")
  @ExecuteOn(TaskExecutors.IO)
  public void index(@PathVariable String path, @Body String body) {
    Value response = scriptService.executeSendMail(path, body);

    Value fromValue = response.getMember("from");
    MailAddress from = new MailAddress();
    from.setName(fromValue.getMember("name").asString());
    from.setEmail(fromValue.getMember("email").asString());

    Value toValue = response.getMember("to");
    MailAddress to = new MailAddress();
    to.setName(toValue.getMember("name").asString());
    to.setEmail(toValue.getMember("email").asString());

    Message message = new Message();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(response.getMember("subject").asString());

    Value content = response.getMember("content");
    assert content.isIterator();
    for (int i = 0; i < content.getArraySize(); i++) {
      Value element = content.getArrayElement(i);
      message.getContent().add(element.asHostObject());
    }

    Value attachments = response.getMember("attachments");
    if (attachments != null) {
      assert attachments.isIterator();
      for (int i = 0; i < attachments.getArraySize(); i++) {
        Value element = attachments.getArrayElement(i);
        message.getAttachments().add(element.asHostObject());
      }
    }

    sendGridService.send(message);
  }
}
