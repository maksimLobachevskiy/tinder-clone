package com.tinder.service;

import com.tinder.dao.Dao;
import com.tinder.entity.Message;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChatService {
  private Dao<Message> messageDao;
  public ChatService(Dao<Message> messageDao) {
    this.messageDao = messageDao;
  }
  public List<Message> getMessagesByRecipientId(int recipientId, int senderId) {
    List<Message> sent = messageDao.getMessages(recipientId, senderId);
    List<Message> received = messageDao.getMessages(senderId, recipientId);
    return Stream.of(sent, received)
            .flatMap(List::stream)
            .distinct()
            .collect(Collectors.toList());
  }

}
