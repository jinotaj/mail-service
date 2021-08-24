package org.jinotaj.service.mail.script;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.exceptions.HttpStatusException;
import org.jinotaj.service.mail.mail.Attachment;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

@Singleton
public class AttachmentHttpClient {
  private final HttpClient httpClient;

  @Inject
  public AttachmentHttpClient(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public Supplier<Attachment> post(String url, Map<String, Object> options, Map<String, Object> mailOptions) {
    return () -> downloadAttachment(url, options, mailOptions);
  }

  private Attachment downloadAttachment(String url, Map<String, Object> options, Map<String, Object> mailOptions) {
    String data = options.get("data").toString();

    MutableHttpRequest<String> request = HttpRequest.POST(url, data);
    if (options.containsKey("headers")) {
      Map<String, String> headers = (Map<String, String>) options.get("headers");
      if (headers.containsKey("Content-Type")) {
        request.contentType(headers.get("Content-Type"));
      }
      if (headers.containsKey("Accept")) {
        request.accept(headers.get("Accept"));
      }
    }

    HttpResponse<byte[]> httpResponse = httpClient.toBlocking()
            .exchange(request, byte[].class);

    int statusCode = httpResponse.code();
    if (statusCode < 200 || statusCode >= 300) {
      throw new HttpStatusException(
              HttpStatus.BAD_REQUEST,
              String.format("Error generating PDF: %s", httpResponse.reason())
      );
    }

    Attachment attachment = new Attachment();

    attachment.setContent(httpResponse.body());

    if (mailOptions.containsKey("filename")) {
      attachment.setFilename(mailOptions.get("filename").toString());
    }
    if (mailOptions.containsKey("type")) {
      attachment.setType(mailOptions.get("type").toString());
    }
    if (mailOptions.containsKey("id")) {
      attachment.setId(mailOptions.get("id").toString());
    }
    if (mailOptions.containsKey("disposition")) {
      attachment.setDisposition(mailOptions.get("disposition").toString());
    }
    return attachment;
  }
}
