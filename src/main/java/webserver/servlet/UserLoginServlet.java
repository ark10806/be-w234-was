package webserver.servlet;

import db.entity.User;
import db.manager.UserManager;
import model.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.HttpStatus;

public class UserLoginServlet extends Servlet {
  Logger logger = LoggerFactory.getLogger(UserLoginServlet.class);

  public UserLoginServlet(SessionManager sessionManager) {
    super(sessionManager);
  }

  private void validateUser(String uid, String password) {
    User criteria = userManager.findById(uid);
    responsePacket.setHttpStatus(HttpStatus.FOUND);
    if (criteria != null && criteria.getPassword().equals(password)) {
      responsePacket.addEntity("Location: /index.html");
      responsePacket.addEntity(String.format("Set-Cookie: logined=%s; Path=/", uid));
      sessionManager.put(uid);
    } else {
      responsePacket.addEntity("Location: /user/login_failed.html");
    }
  }

  @Override
  public void doPost() {
    validateUser(
        requestPacket.body.params.get("userId"),
        requestPacket.body.params.get("password")
    );
  }

}
