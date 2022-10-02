package webserver.servlet;

import db.Database;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.HttpStatus;

public class UserLoginServlet extends Servlet {
  Logger logger = LoggerFactory.getLogger(UserLoginServlet.class);

  public UserLoginServlet(Database db, Session session) {
    super(db, session);
  }

  private void validateUser(String uid, String password) {
    User criteria = db.findUserById(uid);
    responsePacket.setHttpStatus(HttpStatus.FOUND);
    if (criteria != null && criteria.getPassword().equals(password)) {
      responsePacket.addEntity("Location: /index.html");
      responsePacket.addEntity(String.format("Set-Cookie: logined=%s; Path=/", uid));
      sessions.put(uid);
    } else {
      responsePacket.addEntity("Location: /user/login_failed.html");
    }
  }

  @Override
  public void doGet() {
    validateUser(
        requestPacket.header.queryString.get("userId"),
        requestPacket.header.queryString.get("password")
    );
  }

  @Override
  public void doPost() {
    validateUser(
        requestPacket.body.params.get("userId"),
        requestPacket.body.params.get("password")
    );
  }

}
