package com.tinder.controller;

import com.tinder.dao.Dao;
import com.tinder.entity.Message;
import com.tinder.entity.User;
import com.tinder.service.ChatService;

import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatServlet extends HttpServlet {
  private TemplateEngine templateEngine;
  private final ChatService chatService;
  private Dao<Message> messageDao;
  private Dao<User> userDao;

  public ChatServlet(TemplateEngine templateEngine, Dao<Message> messageDao, Dao<User> userDao){
    this.templateEngine=templateEngine;
    this.messageDao = messageDao;
    this.userDao = userDao;
    this.chatService = new ChatService(messageDao, userDao);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    HttpSession session = req.getSession();
    Cookie[] cookies = req.getCookies();
    long who_id = (long) session.getAttribute("id");
    String whom_id = null;
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("receiver")) {
          whom_id = cookie.getValue();
        }
      }
    }


    assert whom_id != null;
    List<Message> messages = chatService.getMessages((int)who_id, Integer.parseInt(whom_id));
    System.out.println(messages.toString());
    User sender = chatService.getUser(who_id);
    User receiver = chatService.getUser(Long.parseLong(whom_id));

    HashMap<String, Object> data = new HashMap<>();

    data.put("receiver_name", receiver.getName());
    data.put("receiver_photo_url", receiver.getUrl());
    data.put("messages", messages);
    data.put("receiver_id", receiver.getId());

    templateEngine.render("messages.ftl", data, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession();
    Cookie[] cookies = req.getCookies();
    long who_id = (long) session.getAttribute("id");
    String whom_id = null;
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("receiver")) {
          whom_id = cookie.getValue();
        }
      }
    }
    assert whom_id != null;

    String text = req.getParameter("message");
    chatService.insertMessage((int)who_id, Integer.parseInt(whom_id), text, LocalDate.now());

    resp.sendRedirect(String.format("/message/%d", Integer.parseInt(whom_id)));
  }

}
