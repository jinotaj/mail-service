package org.jinotaj.service.mail.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.jinotaj.service.mail.script.ScriptService;

/**
 * @author Filip Jirsák
 */
@Controller
public class MailController {
  private final ScriptService service;

  public MailController(ScriptService service) {
    this.service = service;
  }

  @Get("/{+path}")
  public void index(@PathVariable String path) {
    Value script = service.script(path);
    if (script == null) {
      return;
    }
    System.out.println(script);
    System.out.println(script.canExecute());
    System.out.println(script.hasHashEntries());
    System.out.println(script.hasIterator());
    System.out.println(script.hasMembers());

    Value executed = script.execute();
    System.out.println(script);
    System.out.println(script.canExecute());
    System.out.println(script.hasHashEntries());
    System.out.println(script.hasIterator());
    System.out.println(script.hasMembers());

    System.out.println(executed);
    System.out.println(executed.canExecute());
    System.out.println(executed.hasHashEntries());
    System.out.println(executed.hasIterator());
    System.out.println(executed.hasMembers());

//    Context context = service.context();
//    context.eval("js", "sendMail()");
  }
}
