package com.tinder.controller;

import com.tinder.dao.Dao;
import com.tinder.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersLike extends HttpServlet {
  private TemplateEngine templateEngine;
  private Dao<User> userDao;


  public UsersLike(TemplateEngine templateEngine, Dao<User> userDao){
    this.templateEngine=templateEngine;
    this.userDao = userDao;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


  }
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ArrayList<String>nameUsers=new ArrayList<>();
    HashMap<String, Object> data=new HashMap<>();
    for(User user: userDao.getAllInfo()){
      String[] s = user.getName().split(" ");
      nameUsers.add(s[0]);
    }

    data.put("users",nameUsers);
    templateEngine.render("like.ftl", data, resp);

  }

}
