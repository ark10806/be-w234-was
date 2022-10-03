package webserver.servlet;

import model.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.HttpStatus;

public class UserLogoutServlet extends Servlet {
  Logger logger = LoggerFactory.getLogger(UserLogoutServlet.class);

  public UserLogoutServlet(SessionManager sessionManager) {
    super(sessionManager);
  }

  @Override
  public void doGet() {
    String uid = requestPacket.header.cookie.value;
    try {
      sessionManager.remove(uid);
      responsePacket.setHttpStatus(HttpStatus.OK);
    } catch (Exception e) {
      responsePacket.setHttpStatus(HttpStatus.FOUND);
      responsePacket.addEntity("Location: /index.html");
      responsePacket.setBody(HttpStatus.NOT_FOUND.getMessage());
    }
  }

}
