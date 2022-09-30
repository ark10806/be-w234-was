package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import db.Database;

import model.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
  private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
  private static int port = 8080;
  public static Database db = new Database();
  public static Session session = new Session();

  public static void main(final String[] args) throws Exception {
    if (args != null && args.length != 0) {
      port = Integer.parseInt(args[0]);
    }

    // EntityManagerFactory emf = Persistence.createEntityManagerFactory("onboarding");
    //
    // EntityManager em = emf.createEntityManager();
    //
    // EntityTransaction tx = em.getTransaction();
    // tx.begin();
    // String d = "d";
    // try {
    //   User user = new User(d, d, d, "d@naver.com");
    //   Posts posts = new Posts(user, "hi");
    //   em.persist(user);
    //   em.persist(posts);
    //
    //   tx.commit();
    // } catch (Exception e) {
    //   tx.rollback();
    //   e.printStackTrace();
    // } finally {
    //   em.close();
    // }
    //
    // emf.close();


    // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
    try (ServerSocket listenSocket = new ServerSocket(port)) {
      logger.info("Web Application Server started {} port.", port);

      // 클라이언트가 연결될때까지 대기한다.
      Socket connection;
      while ((connection = listenSocket.accept()) != null) {
        Thread thread = new Thread(new RequestHandler(connection, db, session));
        thread.start();
      }
    }
  }
}
