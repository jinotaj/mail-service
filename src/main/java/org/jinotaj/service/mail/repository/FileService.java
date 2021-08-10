package org.jinotaj.service.mail.repository;

import org.jinotaj.service.mail.mail.Content;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Filip Jirsák
 */
public class FileService {
  private final Path directory;

  public FileService(Path directory) {
    this.directory = directory;
  }

  public Content read(String file, String contentType) throws IOException {
    Path path = directory.resolve(file);
    String textContent = Files.readString(path);
    Content content = new Content();
    content.setType(contentType);
    content.setValue(textContent);
    return content;
  }
}
