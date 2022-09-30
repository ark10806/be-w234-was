package webserver.servlet;

import db.Database;
import http.RequestPacket;
import http.ResponsePacket;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import model.Session;
import org.slf4j.Logger;
import webserver.service.HttpStatus;

public class Servlet implements ServletInterface {

  protected static Database db;
  protected static Session sessions;
  public Logger logger;
  protected RequestPacket requestPacket;
  protected ResponsePacket responsePacket;

  public Servlet(Database db, Session sessions) {
    this.db = db;
    this.sessions = sessions;
  }

  @Override
  public String routeView(String url) {
    try {
      return Files.readString(new File(rootDir + url).toPath());
    } catch (IOException e) {
      logger.error("Servlet.routeView: {}", e.getMessage());
      return HttpStatus.NOT_FOUND.getMessage();
    }
  }

  @Override
  public void init(RequestPacket requestPacket, ResponsePacket responsePacket) {
    this.requestPacket = requestPacket;
    this.responsePacket = responsePacket;
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
    } catch (IllegalArgumentException e) {
      responsePacket.setHttpStatus(HttpStatus.BAD_REQUEST);
    } finally {
      destroy();
      return responsePacket;
    }
  }

  @Override
  public void doGet() {

  }

  @Override
  public void doPost() {

  }

  @Override
  public void destroy() {
  }
}
