package com.tinder.controller;

import com.tinder.dao.Dao;
import com.tinder.entity.User;
//import com.tinder.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private Dao<User> userDao;

    public UsersServlet(TemplateEngine templateEngine, Dao<User> userDao){
        this.templateEngine=templateEngine;
        this.userDao = userDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDao.read(1);
        selectionBoolean(req,resp,user);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine.render("users.ftl", new HashMap<>(), resp);

    }
    private void selectionBoolean(HttpServletRequest req, HttpServletResponse resp, User user) {
        String booleanReq = req.getParameter("boolean");

        if (booleanReq != null) {

            if (booleanReq.equals("YES")) {
                checkBtnValue(true, user, req, resp);

            }
            if (booleanReq.equals("NO")) {
                checkBtnValue(false, user, req, resp);
            }
            changeUser(req, resp, user);
            userDao.update(user);
        } else {
            changeUser(req, resp, user);
        }
    }

        private void changeUser(HttpServletRequest req, HttpServletResponse resp,User user){
          String[] s = userDao.getAllInfo().get(user.getCounter()).pretty().split(" ");
          HashMap<String, Object> data = new HashMap<>(3);
          data.put("userName", s[0]);
          data.put("userPhoto", s[1]);
          templateEngine.render("users.ftl", data, resp);
    }

    private void changeLike(HttpServletRequest req, HttpServletResponse resp){
        ArrayList<String> nameUsers=new ArrayList<>();
        HashMap<String, Object> data=new HashMap<>();
        for(User user: userDao.getAllInfo()){
            String[] s = user.getName().split(" ");
            if(s[1].equals("true")){
                nameUsers.add(s[0]);
            }
        }

        data.put("users",nameUsers);
        templateEngine.render("like.ftl", data, resp);
    }

    public void checkBtnValue(boolean bolValue,User user,HttpServletRequest req, HttpServletResponse resp ){
        ArrayList<String>nameUsers=new ArrayList<String>(List.of());
        user.setChoice(bolValue);
        if(user.getCounter()<userDao.getAllInfo().size()){
            int counterUser= user.getCounter();

            user.setCounter(++counterUser);

            if(bolValue){
                String[] s = userDao.getAllInfo().get(counterUser-1).getName().split(" ");

                nameUsers.add(s[0]);
            }}else{
            changeLike(req,resp);
        }

    }


}
