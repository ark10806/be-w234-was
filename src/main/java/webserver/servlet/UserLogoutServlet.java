package webserver.servlet;

import db.Database;
import http.ResponsePacket;
import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.HttpStatus;

public class UserLogoutServlet extends Servlet {
  Logger logger = LoggerFactory.getLogger(UserLogoutServlet.class);

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
      logger.error("request error: {}", e.getMessage());
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

}
