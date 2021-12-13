package com.tinder.controller;

import com.tinder.dao.User;
import com.tinder.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class UsersServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private UserDao userDao;

    public UsersServlet(TemplateEngine templateEngine, UserDao userDao){
        this.templateEngine=templateEngine;
        this.userDao = userDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String booleanReq = req.getParameter("boolean");
        User user = userDao.read(1L);

        if (booleanReq.equals("YES")) {
            user.setChoice(true);
        } else {
            user.setChoice(false);
        }

        userDao.update(user);

        templateEngine.render("users.ftl", new HashMap<>(), resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(userDao.read(1l));
        templateEngine.render("users.ftl", new HashMap<>(), resp);

    }
}
