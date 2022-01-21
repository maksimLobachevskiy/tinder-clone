package com.tinder.controller;

import com.tinder.dao.Dao;

import com.tinder.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;


public class LoginServlet extends HttpServlet {
    private Dao<User> userDao;
    private TemplateEngine templateEngine;

    public LoginServlet(Dao<User> userDao, TemplateEngine templateEngine) {
        this.userDao = userDao;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/users").forward(request, response); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/users").forward(request, response);
    }

}
