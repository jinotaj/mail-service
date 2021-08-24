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

  private final Value bindings;
  private final RepositoryService repositoryService;

  public ScriptServiceImpl(Context context, RepositoryService repositoryService, AttachmentHttpClient attachmentHttpClient) {
    this.context = context;
    this.repositoryService = repositoryService;
    this.bindings = context.getBindings("js");
    this.bindings.putMember("HTTP", attachmentHttpClient);
  }

  @Override
  public Value script(String path) {
    return scripts.computeIfAbsent(path, this::eval);
  }

  @Override
  public Value executeSendMail(String path, String data) {
    Value script = script(path);
    if (script == null) {
      return null;
    }
    Value json = parseJSON(data);
    return script.execute(json);
  }

  @Override
  public Context context() {
    return context;
  }

  @Override
  public Value parseJSON(String data) {
    bindings.putMember("data", data);
    Value json = context.eval("js", "JSON.parse(data)");
    bindings.removeMember("data");
    return json;
  }

  private Value eval(String scriptPath) {
    return context.eval(repositoryService.getSource(scriptPath));
  }
}
