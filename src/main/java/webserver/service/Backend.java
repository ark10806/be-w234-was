package webserver.service;

import db.Database;
import http.RequestPacket;
import http.ResponsePacket;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.servlet.Servlet;
import webserver.servlet.UserCreateServlet;
import webserver.servlet.UserListServlet;
import webserver.servlet.UserLoginServlet;
import webserver.servlet.UserLogoutServlet;

public class Backend {
  private final Logger logger = LoggerFactory.getLogger(Backend.class);
  private Map<String, Servlet> router = new HashMap<>();
  String rootDir = "./webapp/";
  private RequestPacket requestPacket;
  private ResponsePacket responsePacket;

  public Backend(RequestPacket requestPacket, ResponsePacket responsePacket,
      Database db, Session sessions) {
    router.put("/user/create", new UserCreateServlet(db, sessions));
    router.put("/user/login", new UserLoginServlet(db, sessions));
    router.put("/user/list.html", new UserListServlet(db, sessions));
    router.put("/user/logout.html", new UserLogoutServlet(db, sessions));
    // router.put("/qna/show.html", new QnAShowServlet(db, sessions));
    // router.put("/qna/form.html", new QnAListServlet(db, sessions));
    this.requestPacket = requestPacket;
    this.responsePacket = responsePacket;
  }

  public ResponsePacket route() {
    String url = requestPacket.header.url;
    try {
      responsePacket.setHttpVersion(requestPacket.header.httpVersion);
      responsePacket.setBody(routeView(url));
      responsePacket.setContentType(requestPacket.header.entity.get("Accept:"));
    } catch (IOException e) {
      logger.debug(e.getMessage());
      responsePacket.setHttpStatus(HttpStatus.NOT_FOUND);
      responsePacket.setBody(HttpStatus.NOT_FOUND.getMessage());
    } finally {
      if (router.containsKey(url)) {
        router.get(url).init(requestPacket, responsePacket);
        responsePacket = router.get(url).run();
      }
      return responsePacket;
    }
  }

  private String routeView(String url) throws IOException {
    try {
      System.out.printf("fontError: %s\n", new File(rootDir + url).toPath());
      return Files.readString(new File(rootDir + url).toPath());
    } catch (IOException e) {
      logger.error("Backend.routeView: {} on {}", e.getMessage(), url);
      throw e;
    }
  }
}
