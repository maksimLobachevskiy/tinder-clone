package com.tinder.controller;

import com.tinder.dao.Dao;

import com.tinder.dao.UserJdbcDao;
import com.tinder.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

//@WebFilter(filterName = "/*")
public class LoginFilter implements Filter {
    private TemplateEngine templateEngine;
    private Dao<User> userDao;

    public LoginFilter(TemplateEngine templateEngine, Dao<User> userDao) {
        this.templateEngine = templateEngine;
        this.userDao = userDao;
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (request.getSession(false) == null) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            User user = userDao.findByLoginPass(login, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("id", user.getId());
                request.getRequestDispatcher("/users").forward(request, response);
            }

            templateEngine.render("login.ftl", new HashMap<>(), response);
        }
        chain.doFilter(request, response);
    }


}
