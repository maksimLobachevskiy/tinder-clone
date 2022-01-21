package com.tinder.service;

import com.tinder.dao.Dao;
import com.tinder.entity.Message;
import com.tinder.entity.User;

import java.time.LocalDate;
import java.util.List;


public class ChatService {
  private Dao<Message> messageDao;
  private Dao<User> userDao;

  public ChatService(Dao<Message> messageDao,  Dao<User> userDao) {
    this.messageDao = messageDao;
    this.userDao = userDao;
  }
  public List<Message> getMessages(int who_id, int whom_id) {
    return messageDao.getMessages(who_id, whom_id);
  }

  public User getUser(Long id) {
   return userDao.read(id);
  }

  public void insertMessage(int who_id, int whom_id, String text, LocalDate date) {
    messageDao.insert(new Message(who_id, whom_id, text, date));
  }

}