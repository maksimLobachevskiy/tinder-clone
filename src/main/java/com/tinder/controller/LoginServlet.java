package com.tinder.controller;

import com.tinder.dao.User;
import com.tinder.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

//@WebServlet(urlPatterns = "/")
public class LoginServlet extends HttpServlet {
    private UserDao userDao;
    private TemplateEngine templateEngine;

    public LoginServlet(UserDao userDao, TemplateEngine templateEngine) {
        this.userDao = userDao;
        this.templateEngine = templateEngine;
    }

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       templateEngine.render("login.ftl", new HashMap<>(), response);
    }
}
