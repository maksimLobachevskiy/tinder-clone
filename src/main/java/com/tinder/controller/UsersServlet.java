package com.tinder.controller;

import com.tinder.dao.User;
import com.tinder.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private UserDao userDao;


    public UsersServlet(TemplateEngine templateEngine, UserDao userDao){
        this.templateEngine=templateEngine;
        this.userDao = userDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        User user = userDao.read(1L);

        selectionBoolean(req,resp,user);

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine.render("users.ftl", new HashMap<>(), resp);

    }
    private void selectionBoolean(HttpServletRequest req, HttpServletResponse resp,User user ){
        String booleanReq = req.getParameter("boolean");



        if(user.getCounter()<userDao.getAllUserInfo().size()-1){
            int counterUser= user.getCounter();

            counterUser++;
            user.setCounter(counterUser);


        }else {
            user.setCounter(0);
            user.setChoice(false);
        }
        if (booleanReq.equals("YES")) {
            user.setChoice(true);

        } else {
            user.setChoice(false);


        }



        userDao.update(user);
        changeUser(req,resp,user);


    }
    private void changeUser(HttpServletRequest req, HttpServletResponse resp,User user){


          String s[] = userDao.getAllUserInfo().get(user.getCounter()).split(" ");
          HashMap<String, Object> data = new HashMap<>(3);
          data.put("userName", s[0]);
          data.put("userPhoto", s[1]);

          templateEngine.render("users.ftl", data, resp);



    }
}
