package com.tinder.controller;

import com.tinder.dao.Dao;
import com.tinder.entity.User;
import org.eclipse.jetty.server.session.Session;

import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
  private TemplateEngine templateEngine;
  public LogoutServlet(TemplateEngine templateEngine) {

    this.templateEngine = templateEngine;
  }
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      HttpSession session = req.getSession(false);
      if (session != null) {
        session.invalidate();
        resp.sendRedirect("/login");
      }
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }

}
