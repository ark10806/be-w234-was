package webserver.servlet;

import db.entity.User;
import exception.UserException;
import exception.UserExceptionMessage;
import model.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.HttpStatus;

public class UserLoginServlet extends Servlet {
  Logger logger = LoggerFactory.getLogger(UserLoginServlet.class);
  private SessionManager sessionManager = new SessionManager();


  private void validateUser(String uid, String password) {
    responsePacket.setHttpStatus(HttpStatus.FOUND);
    try {
      User criteria = userManager.findById(uid);
      if (!criteria.getPassword().equals(password)) {
        throw new UserException(UserExceptionMessage.INVALID_USER_PARAMETERS);
      }
      responsePacket.addEntity("Location: /index.html");
      responsePacket.addEntity(String.format("Set-Cookie: logined=%s; Path=/", uid));
      sessionManager.put(uid);
    } catch (UserException e) {
      responsePacket.addEntity("Location: /user/login_failed.html");
    } catch (Exception e) {
      responsePacket.setBody(e.getMessage());
      e.printStackTrace();
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
