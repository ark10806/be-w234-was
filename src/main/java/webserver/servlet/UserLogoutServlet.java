package webserver.servlet;

import db.Database;
import http.ResponsePacket;
import model.Session;
import webserver.service.HttpStatus;

public class UserLogoutServlet extends Servlet {

  public UserLogoutServlet(Database db, Session session) {
    super(db, session);
  }

  @Override
  public ResponsePacket run() {
    try {
      if ("GET".equals(requestPacket.header.method)) {
        doGet();
      }
      if ("POST".equals(requestPacket.header.method)) {
        doPost();
      }
      responsePacket.setHttpStatus(HttpStatus.OK);
    } catch (Exception e) {
      responsePacket.setHttpStatus(HttpStatus.FOUND);
      responsePacket.addEntity("Location: /index.html");
      responsePacket.setBody(HttpStatus.NOT_FOUND.getMessage());
      e.printStackTrace();
    } finally {
      destroy();
      return responsePacket;
    }
  }

  @Override
  public void doGet() {
    String uid = requestPacket.header.cookie.value;
    try {
      sessions.remove(uid);
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public void doPost() {
  }

  @Override
  public void destroy() {
  }
}
