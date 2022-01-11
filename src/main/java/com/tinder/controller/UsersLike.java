package com.tinder.controller;

import com.tinder.dao.Dao;
import com.tinder.entity.Like;
import com.tinder.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UsersLike extends HttpServlet {
    private TemplateEngine templateEngine;
    private Dao<User> userDao;
    private Dao<Like> likeDao;


    public UsersLike(TemplateEngine templateEngine, Dao<User> userDao, Dao<Like> likeDao) {
        this.templateEngine = templateEngine;
        this.userDao = userDao;
        this.likeDao = likeDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session != null) {
            Long id =(long) session.getAttribute("id");
            if(id != null) {
                User user = userDao.read(id);
                List<String> nameUsers = new ArrayList<>(List.of());

                HashMap<String, Object> data  ;
                likeDao.findAll(user);
                userDao.findAll(user);
                List<Like>filterCol=  likeDao.getAllInfo().stream().filter(f -> f.getWho_id() == user.getId() && f.isReaction()).collect(Collectors.toList());


                filterCol.forEach(f ->{
                    userDao.getAllInfo().stream().filter(s -> s.getId()== f.getWhom_id()).forEach(s -> nameUsers.add(s.getName()));
                });
                if (nameUsers.size() == 0) {
                    try {
                        resp.getWriter().println("<h1>The list is empty!</h1>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    List<String> distList  =nameUsers.stream().distinct().collect(Collectors.toList());
                    data = new HashMap<>(Collections.singletonMap("users", distList));
                    templateEngine.render("like.ftl", data, resp);

                }


            }    } else {
                req.getRequestDispatcher("/").forward(req, resp);
            }}
};
