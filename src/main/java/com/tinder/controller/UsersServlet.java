package com.tinder.controller;

import com.tinder.dao.Dao;
import com.tinder.entity.Like;
import com.tinder.entity.User;
//import com.tinder.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private Dao<User> userDao;
    private Dao<Like> likeDao;

    public UsersServlet(TemplateEngine templateEngine, Dao<User> userDao, Dao<Like> likeDao) {
        this.templateEngine = templateEngine;
        this.userDao = userDao;
        this.likeDao=likeDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session != null) {
            Long id =(long) session.getAttribute("id");
            if(id != null) {
                User user = userDao.read(id);
                userDao.findAll( user);

                selectionBoolean(req, resp, user);     } else {
                req.getRequestDispatcher("/").forward(req, resp);

            }}




//        User user1 = userDao.read(1L);
//        User user2 = userDao.read(2L);
//        User user3 = userDao.read(3L);
//        user1.setCounter(0);
//        user2.setCounter(0);
//        user3.setCounter(0);
//        userDao.update(user1);
//        userDao.update(user2);
//        userDao.update(user3);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);

    }

    private void selectionBoolean(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        String booleanReq = req.getParameter("boolean");
        HttpSession session = req.getSession();
        Boolean test = (Boolean) session.getAttribute("boolean");
        if (booleanReq != null && test != null) {

            if (booleanReq.equals("YES")) {
                checkBtnValue(true, user, req, resp);

            }
            if (booleanReq.equals("NO")) {
                checkBtnValue(false, user, req, resp);
            }


        } else if(user.getCounter() == 0){
            changeUser(req, resp, user);
            session.setAttribute("boolean", true);

        }else {req.getRequestDispatcher("/like").forward(req,resp);};
    }

    private void changeUser(HttpServletRequest req, HttpServletResponse resp, User user) {

        User currentUser = userDao.getAllInfo().get(user.getCounter());
        HashMap<String, Object> data = new HashMap<>();

        data.put("userName", currentUser.getName());
        data.put("userPhoto", currentUser.getUrl());

        templateEngine.render("users.ftl", data, resp);
    }



    public void checkBtnValue(boolean bolValue, User user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int counterUser = user.getCounter();
        Like newLike;

        User currentUser = userDao.getAllInfo().get(counterUser);

        newLike=  new Like(user.getId(),currentUser.getId(),bolValue);
        likeDao.create(newLike);

        if (user.getCounter() >= userDao.getAllInfo().size() - 1) {
        req.getRequestDispatcher("/like").forward(req,resp);


        } else {
            user.setCounter(++counterUser);

            changeUser(req, resp, user);
            userDao.update(currentUser);
           ; userDao.update(user);

        }

    }


}
