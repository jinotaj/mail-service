package org.jinotaj.service.mail.repository;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.runtime.context.scope.Refreshable;
import org.graalvm.polyglot.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Filip Jirsák
 */
@Refreshable
public class RepositoryService {
  private final Logger logger = LoggerFactory.getLogger(RepositoryService.class);

  private final RepositoryConfig config;
  private final Map<String, Source> sources = new HashMap<>();
  private final FileService fileService;

  public RepositoryService(RepositoryConfig config) {
    this.config = config;
    this.fileService = new FileService(config.getDirectory());
  }

  @PostConstruct
  void init() throws IOException {
    Files.walkFileTree(config.getDirectory(), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new JavaScriptFileVisitor());
  }

  public Source getSource(String path) {
    Source source = sources.get(path);
    if (source == null) {
      throw new HttpStatusException(HttpStatus.NOT_FOUND, String.format("Script '%s.js' not found.", path));
    }
    return source;
  }

  public FileService getFileService() {
    return this.fileService;
  }

  private void processFile(Path file) {
    String path = config.getDirectory().relativize(file).toString();
    StringBuilder pathBuilder = new StringBuilder(path);
    //pathBuilder.insert(0, '/'); //TODO
    String mimeType = null;
    if (path.endsWith(".js")) {
      pathBuilder.setLength(pathBuilder.length() - 3);
//      mimeType = "application/javascript";
    } else if (path.endsWith(".mjs")) {
      pathBuilder.setLength(pathBuilder.length() - 4);
//      mimeType = "application/javascript";
    }

    try {
      Source source = Source.newBuilder("js", file.toFile())
//              .mimeType(mimeType)
              .build();
      sources.put(pathBuilder.toString(), source);
    } catch (IOException e) {
      logger.error("Chyba při načítání souboru {}.", file, e);
    }
  }

  private class JavaScriptFileVisitor implements FileVisitor<Path> {
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
      if (dir.getFileName().toString().startsWith(".")) {
        return FileVisitResult.SKIP_SUBTREE;
      }
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      String name = file.getFileName().toString();
      if (name.endsWith(".js") || name.endsWith(".mjs")) {
        processFile(file);
      }
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
      //TODO
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
      return FileVisitResult.CONTINUE;
    }
  }

}
