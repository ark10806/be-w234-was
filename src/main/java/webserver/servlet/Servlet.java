package webserver.servlet;

import db.manager.UserManager;
import http.RequestPacket;
import http.ResponsePacket;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.slf4j.Logger;
import webserver.service.HttpStatus;

public class Servlet implements ServletInterface {

  public Logger logger;
  protected RequestPacket requestPacket;
  protected ResponsePacket responsePacket;
  protected static UserManager userManager = new UserManager();

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
  public Servlet init(RequestPacket requestPacket, ResponsePacket responsePacket) {
    this.requestPacket = requestPacket;
    this.responsePacket = responsePacket;
    return this;
  }

  @Override
  public ResponsePacket run() {
    try {
      switch (requestPacket.header.method) {
        case "GET":
          doGet();
          break;
        case "POST":
          doPost();
          break;
        default:
          break;
      }
    } catch (Exception e) {
      responsePacket.setHttpStatus((HttpStatus.BAD_REQUEST));
      responsePacket.setBody(HttpStatus.BAD_REQUEST.getMessage());
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
