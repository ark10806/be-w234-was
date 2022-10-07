package webserver.servlet;

import db.entity.User;
import exception.UserException;
import java.util.Map;
import javax.management.openmbean.KeyAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.HttpStatus;

public class UserCreateServlet extends Servlet {
  Logger logger = LoggerFactory.getLogger(UserCreateServlet.class);

  private void signUp(Map<String, String> userInfo) {
    try {
      userManager.insert(new User(
          userInfo.get("userId"),
          userInfo.get("password"),
          userInfo.get("name"),
          userInfo.get("email")
      ));
      responsePacket.setHttpStatus(HttpStatus.FOUND);
      responsePacket.addEntity("Location: /user/login.html");
    } catch (UserException e) {
      responsePacket.setHttpStatus(HttpStatus.FOUND);
      responsePacket.addEntity("Location: /user/form_exists.html");
      logger.debug(e.getMessage());
    } catch (Exception e) {
      responsePacket.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      responsePacket.setBody(HttpStatus.INTERNAL_SERVER_ERROR.getMessage());
      logger.debug("bad request occured: {}", e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void doGet() throws KeyAlreadyExistsException {
    signUp(requestPacket.header.queryString);
  }

  @Override
  public void doPost() throws KeyAlreadyExistsException {
    signUp(requestPacket.body.params);
  }

}
