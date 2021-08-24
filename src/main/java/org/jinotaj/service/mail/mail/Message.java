package org.jinotaj.service.mail.mail;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Filip Jirsák
 */
public class Message {
 private MailAddress from;
 private MailAddress to;
 private String subject;
 private List<Content> content = new LinkedList<>();
 private List<Supplier<Attachment>> attachments = new LinkedList<>();

 public MailAddress getFrom() {
  return from;
 }

 public void setFrom(MailAddress from) {
  this.from = from;
 }

 public MailAddress getTo() {
  return to;
 }

 public void setTo(MailAddress to) {
  this.to = to;
 }

 public String getSubject() {
  return subject;
 }

 public void setSubject(String subject) {
  this.subject = subject;
 }

 public List<Content> getContent() {
  return content;
 }

 public void setContent(List<Content> content) {
  this.content = content;
 }

 public List<Supplier<Attachment>> getAttachments() {
  return attachments;
 }

 public void setAttachments(List<Supplier<Attachment>> attachments) {
  this.attachments = attachments;
 }
}
