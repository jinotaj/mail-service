package org.jinotaj.service.mail.script;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;
import org.jinotaj.service.mail.repository.RepositoryService;

import javax.inject.Singleton;

/**
 * @author Filip Jirsák
 */
@Singleton
@Factory
public class ScriptServiceFactory {
  private final Engine engine;
  private final Context.Builder contextBuilder;
  private final RepositoryService repositoryService;

  public ScriptServiceFactory(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
    this.engine = Engine.create();
    this.contextBuilder = Context.newBuilder("js")
            .allowAllAccess(true)
            .engine(engine);
  }

  @Bean
  public ScriptService scriptService() {
    Context context = contextBuilder.build();
    Value bindings = context.getBindings("js");
    bindings.putMember("File", repositoryService.getFileService());
    bindings.putMember("HTTP", "");
    return new ScriptServiceImpl(context, repositoryService);
  }
}
