package webserver.servlet;

import db.entity.User;
import http.Cookie;
import java.util.List;
import model.SessionManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.HttpStatus;

public class UserListServlet extends Servlet {
  Logger logger = LoggerFactory.getLogger(UserListServlet.class);
  private SessionManager sessionManager = new SessionManager();
  private Cookie cookie;

  public String makeUserTable() {
    StringBuilder userTable = new StringBuilder();
    List<String> online = sessionManager.getAll();
    String newline = System.getProperty("line.separator");

    for (int i = 0; i < online.size(); i++) {
      User user = userManager.findById(online.get(i));
      userTable
          .append(newline)
          .append("<tr>")
          .append(newline)
          .append("<th scope=\"row\">")
          .append(i + 1)
          .append("</th> <td>")
          .append(user.getUserId())
          .append("</td> <td>")
          .append(user.getName())
          .append("</td> <td>")
          .append(user.getEmail())
          .append("</td>")
          .append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n</tr>");
    }
    return userTable.toString();
  }

  @Override
  public void doGet() {
    try {
      cookie = requestPacket.header.cookie;
      if (cookie == null || !sessionManager.check(cookie.value)) {
        responsePacket.setHttpStatus(HttpStatus.FOUND);
        responsePacket.addEntity("Location: /user/login.html");
      } else {
        String html = routeView("/user/list.html");
        Document doc = Jsoup.parse(html);
        doc.getElementById("user-table").append(makeUserTable());
        responsePacket.setHttpStatus(HttpStatus.OK);
        responsePacket.setBody(doc.toString());
      }
    } catch (Exception e) {
      throw e;
    }
  }

}
