package com.tinder.controller;

import com.tinder.dao.Dao;
import com.tinder.entity.Message;
import com.tinder.entity.User;
import com.tinder.service.ChatService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ChatServlet extends HttpServlet {
  private TemplateEngine templateEngine;
  private ChatService chatService;
  private Dao<Message> messageDao;
  private Dao<User> userDao;

  public ChatServlet(TemplateEngine templateEngine, Dao<Message> messageDao, Dao<User> userDao, ChatService chatService){
    this.templateEngine=templateEngine;
    this.messageDao = messageDao;
    this.userDao = userDao;
    this.chatService = chatService;
  }
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

    try {
      HashMap<String, Object> data = new HashMap<>();
      String path = req.getPathInfo();
      int id = Integer.parseInt(path.substring(1));
      data.put("messages", chatService.getMessagesByRecipientId(id, 100));
      data.put("user", 100);
      data.put("recipient", userDao.read(id).getId().toString());
     templateEngine.render("messages.ftl", data, resp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
