package com.tinder.controller;

import com.tinder.dao.User;
import com.tinder.dao.UserDao;

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

//@WebFilter(filterName = "/*")
public class LoginFilter implements Filter {
    private TemplateEngine templateEngine;
    private UserDao userDao;

    public LoginFilter(TemplateEngine templateEngine, UserDao userDao) {
        this.templateEngine = templateEngine;
        this.userDao = userDao;
    }
    public void init(FilterConfig config) throws ServletException {    }
    public void destroy() {    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if ((request.getPathInfo() != null && request.getPathInfo().split("/").length > 1)
                && (request.getPathInfo().split("/")[1].equals("css"))) {
            chain.doFilter(request, response);
        }
        if (request.getSession(false) != null) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            User user = userDao.findByLoginPass(login, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
            }
        } else
        chain.doFilter(request, response);
        request.getRequestDispatcher("/login").forward(request, response);
    }
}
