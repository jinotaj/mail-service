package org.jinotaj.service.mail.repository;

import io.micronaut.context.annotation.ConfigurationProperties;

import java.nio.file.Path;

/**
 * @author Filip Jirsák
 */
@ConfigurationProperties("mail-service.repositories.default")
public class RepositoryConfig {
  private final String name = "default";
  private Path directory;

  public String getName() {
    return name;
  }

  public Path getDirectory() {
    return directory;
  }

  public void setDirectory(Path directory) {
    this.directory = directory;
  }
}
