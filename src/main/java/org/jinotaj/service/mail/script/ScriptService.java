package org.jinotaj.service.mail.script;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

/**
 * @author Filip Jirsák
 */
public interface ScriptService {
  Value script(String path);

  Context context();
}
