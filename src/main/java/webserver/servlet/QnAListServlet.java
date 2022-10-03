// package webserver.servlet;
//
// import java.util.List;
//
// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import db.Database;
// import http.ResponsePacket;
// import model.Session;
// import model.User;
// import webserver.service.HttpStatus;
//
// public class QnAListServlet extends Servlet {
//   Logger logger = LoggerFactory.getLogger(QnAListServlet.class);
//
//   public QnAListServlet(Database db, Session session) {
//     super(db, session);
//   }
//
//   @Override
//   public ResponsePacket run() {
//     try {
//       if ("GET".equals(requestPacket.header.method)) {
//         doGet();
//       }
//       if ("POST".equals(requestPacket.header.method)) {
//         doPost();
//       }
//       responsePacket.setHttpStatus(HttpStatus.OK);
//       return responsePacket;
//     } catch (IllegalArgumentException e) {
//       responsePacket.setHttpStatus(HttpStatus.BAD_REQUEST);
//       responsePacket.setBody(HttpStatus.BAD_REQUEST.getMessage());
//     } finally {
//       destroy();
//       return responsePacket;
//     }
//   }
//
//   private String makeQnAList() {
//     StringBuilder qna = new StringBuilder();
//     // List<String> posts = db.
//   }
//
//   @Override
//   public void doGet() {
//     String html = routeView("/index.html");
//     Document doc = Jsoup.parse(html);
//     doc.getElementById("qna-list").append(makeQnAList());
//     responsePacket.setBody(doc.toString());
//     try {
//       db.addUser(new User(
//         requestPacket.header.queryString.get("userId"),
//         requestPacket.header.queryString.get("password"),
//         requestPacket.header.queryString.get("name"),
//         requestPacket.header.queryString.get("email")
//       ));
//     } catch (IllegalArgumentException e) {
//       throw e;
//     }
//   }
//
//   @Override
//   public void doPost() {
//     try {
//       db.addUser(new User(
//         requestPacket.body.params.get("userId"),
//         requestPacket.body.params.get("password"),
//         requestPacket.body.params.get("name"),
//         requestPacket.body.params.get("email")
//       ));
//     } catch (IllegalArgumentException e) {
//       throw e;
//     }
//   }
//
//   @Override
//   public void destroy() {
//   }
// }
