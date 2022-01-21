package com.tinder.controller;

import com.tinder.dao.Dao;
import com.tinder.entity.Like;
import com.tinder.entity.User;
import org.eclipse.jetty.server.session.Session;

import javax.servlet.ServletException;
import javax.servlet.http.*;
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

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session != null) {
            Long id =(long) session.getAttribute("id");
            if(id != null) {
                User user = userDao.read(id);
                List<User> nameUsers = new ArrayList<>(List.of());

                HashMap<String, Object> data  ;
                likeDao.findAll(user);
                userDao.findAll(user);
                List<Like>filterCol=  likeDao.getAllInfo().stream().filter(f -> f.getWho_id() == user.getId() && f.isReaction()).collect(Collectors.toList());


                filterCol.forEach(f ->{
                    userDao.getAllInfo().stream().filter(s -> s.getId()== f.getWhom_id()).forEach(s -> nameUsers.add(s));
                });
                if (nameUsers.size() == 0) {
                    try {
                        resp.getWriter().println("<h1>The list is empty!</h1>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    List<User> distList  =nameUsers.stream().distinct().collect(Collectors.toList());
                    data = new HashMap<>(Collections.singletonMap("users", distList));
                    templateEngine.render("like.ftl", data, resp);

                }


            }    } else {
                req.getRequestDispatcher("/").forward(req, resp);
            }}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
        String id = req.getParameter("name");
        User user = userDao.read(Long.parseLong(id));
        Cookie c = new Cookie("receiver",id);
        resp.addCookie(c);
        resp.sendRedirect(String.format("/message/%d", user.getId()));
    } catch (Exception e) {
        resp.sendRedirect("/users");
    }
}
};
