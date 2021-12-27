package com.tinder.controller;

import com.tinder.dao.Dao;
import com.tinder.entity.User;
//import com.tinder.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private Dao<User> userDao;

    public UsersServlet(TemplateEngine templateEngine, Dao<User> userDao) {
        this.templateEngine = templateEngine;
        this.userDao = userDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDao.read(2L);
        userDao.findAll();
        selectionBoolean(req, resp, user);
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


        } else {user.setCounter(0);
            userDao.update(user);
            changeUser(req, resp, user);
            session.setAttribute("boolean", true);

        }
    }

    private void changeUser(HttpServletRequest req, HttpServletResponse resp, User user) {

        User currentUser = userDao.getAllInfo().get(user.getCounter());
        HashMap<String, Object> data = new HashMap<>();

        data.put("userName", currentUser.getName());
        data.put("userPhoto", currentUser.getUrl());

        templateEngine.render("users.ftl", data, resp);
    }

    private void changeLike(HttpServletRequest req, HttpServletResponse resp) {
        List<String> nameUsers = new ArrayList<>(List.of());

        HashMap<String, Object> data = new HashMap<>();
        userDao.findAll();

        userDao.getAllInfo().stream().filter(f -> f.getChoice() != null && f.getChoice()).forEach(f -> nameUsers.add(f.getName()));
        if (nameUsers.size() == 0) {
            try {
                resp.getWriter().println("<h1>The list is empty!</h1>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            data.put("users", nameUsers);
            ;
        }

        templateEngine.render("like.ftl", data, resp);
    }

    public void checkBtnValue(boolean bolValue, User user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int counterUser = user.getCounter();


        User currentUser = userDao.getAllInfo().get(counterUser);
        if (currentUser.equals(user)) {
            user.setChoice(bolValue);
        } else {
            currentUser.setChoice(bolValue);
        }
        userDao.update(currentUser);


        if (user.getCounter() >= userDao.getAllInfo().size() - 1) {
            changeLike(req, resp);


        } else {
            user.setCounter(++counterUser);

            changeUser(req, resp, user);
            userDao.update(user);

        }

    }


}
