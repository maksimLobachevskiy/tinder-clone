package com.tinder.entity;

public class Message {
  private int id;
  private String message;
  private int senderId;
  private int recipientId;

  public Message(int id, String message, int senderId, int recipientId) {
    this.id = id;
    this.message = message;
    this.senderId = senderId;
    this.recipientId = recipientId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getSenderId() {
    return senderId;
  }

  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  public int getRecipientId() {
    return recipientId;
  }

  public void setRecipientId(int recipientId) {
    this.recipientId = recipientId;
  }

  @Override
  public String toString() {
    return "Chat{" +
            "id=" + id +
            ", message='" + message + '\'' +
            ", senderId=" + senderId +
            ", recipientId=" + recipientId +
            '}';
  }
}
