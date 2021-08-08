package org.jinotaj.service.mail.script;

import io.micronaut.runtime.context.scope.ThreadLocal;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.jinotaj.service.mail.repository.RepositoryService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Filip Jirsák
 */
@ThreadLocal
public class ScriptServiceImpl implements ScriptService {
  private final Map<String, Value> scripts = new HashMap<>();
  private final Context context;
  private final RepositoryService repositoryService;

  public ScriptServiceImpl(Context context, RepositoryService repositoryService) {
    this.context = context;
    this.repositoryService = repositoryService;
  }

  @Override
  public Value script(String path) {
    return scripts.computeIfAbsent(path, scriptPath -> context.parse(repositoryService.getSource(scriptPath)));
  }

  @Override
  public Context context() {
    return context;
  }

}
