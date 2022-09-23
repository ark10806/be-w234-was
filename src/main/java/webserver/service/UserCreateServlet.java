package webserver.service;

import http.ResponsePacket;
import model.User;

public class UserCreateServlet extends Servlet {

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
		} catch (IllegalArgumentException e) {
			responsePacket.setHttpStatus(HttpStatus.BAD_REQUEST);
			view = "Sign up failed";
		} finally {
			responsePacket.setBody(view);
			destroy();
			return responsePacket;
		}
	}

	@Override
	public void doGet() {
		try {
			db.addUser(new User(
				requestPacket.header.queryString.get("userId"),
				requestPacket.header.queryString.get("password"),
				requestPacket.header.queryString.get("name"),
				requestPacket.header.queryString.get("email")
			));
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	@Override
	public void doPost() {
		try {
			db.addUser(new User(
				requestPacket.body.params.get("userId"),
				requestPacket.body.params.get("password"),
				requestPacket.body.params.get("name"),
				requestPacket.body.params.get("email")
			));
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	@Override
	public void destroy() {
	}
}
