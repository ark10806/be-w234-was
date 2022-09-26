package webserver.servlet;

import db.Database;
import http.Cookie;
import http.ResponsePacket;
import java.util.List;
import model.Session;
import model.User;
import webserver.service.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UserListServlet extends Servlet {
	Cookie cookie;
	public UserListServlet(Database db, Session session) { super(db, session); }
	@Override
	public ResponsePacket run() {
		responsePacket.setHttpStatus(HttpStatus.OK);
		cookie = requestPacket.header.cookie;
		try {
			if ("GET".equals(requestPacket.header.method)) {
				doGet();
			}
			if ("POST".equals(requestPacket.header.method)) {
				doPost();
			}
			return responsePacket;
		} catch (Exception e) {
			responsePacket.setHttpStatus(HttpStatus.BAD_REQUEST);
			responsePacket.setBody(HttpStatus.BAD_REQUEST.getMessage());
		} finally {
			destroy();
			return responsePacket;
		}
	}

	public String makeUserTable() {
		StringBuilder userTable = new StringBuilder();
		List<String> online = sessions.getAll();

		for (int i = 0; i < online.size(); i++) {
			User user = db.findUserById(online.get(i));
			userTable.append(String.format(
				"\n<tr>\n<th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td>"
					+ "<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n</tr>",
				i + 1, user.getUserId(), user.getName(), user.getEmail()));
		}
		return userTable.toString();
	}

	@Override
	public void doGet() {
		if (!sessions.check(cookie.value)) {
			responsePacket.setHttpStatus(HttpStatus.FOUND);
			responsePacket.addEntity("Location: /user/login.html");
		} else {
			String html = routeView("/user/list.html");
			Document doc = Jsoup.parse(html);
			doc.getElementById("user-table").append(makeUserTable());
			responsePacket.setBody(doc.toString());
		}
	}

	@Override
	public void doPost() {
	}

	@Override
	public void destroy() {
	}
}
