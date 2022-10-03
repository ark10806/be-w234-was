package webserver;

import http.RequestPacket;
import http.ResponsePacket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import model.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.Backend;

public class RequestHandler implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

  private Socket connection;
  private RequestPacket reqPacket;
  private ResponsePacket resPacket;
  private Backend backend;
  private SessionManager sessionManager;

  public RequestHandler(Socket connectionSock, SessionManager sessionManager) {
    this.connection = connectionSock;
    this.sessionManager = sessionManager;
  }

  public void run() {
    logger.debug("New Client Connect! Connected IP : {}, Port : {}",
      connection.getInetAddress(), connection.getPort());

    try (InputStream in = connection.getInputStream();
         OutputStream out = connection.getOutputStream()) {
      // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
      reqPacket = new RequestPacket(in);
      reqPacket.prn();

      resPacket = new ResponsePacket(out);
      backend = new Backend(reqPacket, resPacket, sessionManager);
      resPacket = backend.route();
      resPacket.flush();
      resPacket.prn();
    } catch (IOException e) {
      logger.error(e.getMessage());
    } finally {
      try {
        connection.close();
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }
  }
}
