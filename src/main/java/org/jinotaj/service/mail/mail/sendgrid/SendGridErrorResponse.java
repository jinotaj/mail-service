package org.jinotaj.service.mail.mail.sendgrid;

import io.micronaut.core.annotation.Introspected;

import java.util.List;

/**
 * @author Filip Jirsák
 */
@Introspected
public class SendGridErrorResponse {
 private String id;
 private List<SendGridError> errors;

 public String getId() {
  return id;
 }

 public void setId(String id) {
  this.id = id;
 }

 public List<SendGridError> getErrors() {
  return errors;
 }

 public void setErrors(List<SendGridError> errors) {
  this.errors = errors;
 }
}
