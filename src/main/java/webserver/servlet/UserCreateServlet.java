package webserver.servlet;

import db.Database;
import http.ResponsePacket;
import javax.management.openmbean.KeyAlreadyExistsException;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.HttpStatus;

public class UserCreateServlet extends Servlet {
  Logger logger = LoggerFactory.getLogger(UserCreateServlet.class);

  public UserCreateServlet(Database db, Session session) {
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
      responsePacket.setHttpStatus(HttpStatus.FOUND);
      responsePacket.addEntity("Location: /user/login.html");
      return responsePacket;
    } catch (KeyAlreadyExistsException e) {
      responsePacket.setHttpStatus(HttpStatus.FOUND);
      responsePacket.addEntity("Location: /user/form_exists.html");
      logger.debug("userId [{}] already in used");
    } catch (IllegalArgumentException e) {
      responsePacket.setHttpStatus(HttpStatus.BAD_REQUEST);
      responsePacket.setBody(HttpStatus.BAD_REQUEST.getMessage());
      logger.debug("bad request occured: {}", e.getMessage());
    } finally {
      destroy();
      return responsePacket;
    }
  }

  @Override
  public void doGet() throws KeyAlreadyExistsException {
    try {
      db.addUser(new User(
          requestPacket.header.queryString.get("userId"),
          requestPacket.header.queryString.get("password"),
          requestPacket.header.queryString.get("name"),
          requestPacket.header.queryString.get("email")
      ));
    } catch (KeyAlreadyExistsException e) {
      throw e;
    }
  }

  @Override
  public void doPost() throws KeyAlreadyExistsException {
    try {
      db.addUser(new User(
          requestPacket.body.params.get("userId"),
          requestPacket.body.params.get("password"),
          requestPacket.body.params.get("name"),
          requestPacket.body.params.get("email")
      ));
    } catch (KeyAlreadyExistsException e) {
      throw e;
    }
  }

  @Override
  public void destroy() {
  }
}
