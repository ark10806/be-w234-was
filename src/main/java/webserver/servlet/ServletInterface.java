package webserver.servlet;

import http.RequestPacket;
import http.ResponsePacket;

public interface ServletInterface {
  String rootDir = "./webapp/";

  String routeView(String url);

  void init(RequestPacket requestPacket, ResponsePacket responsePacket);

  ResponsePacket run();

  void doGet();

  void doPost();

  void destroy();

}
