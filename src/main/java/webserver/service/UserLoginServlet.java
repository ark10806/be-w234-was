package webserver.service;

import http.ResponsePacket;
import model.User;

public class UserLoginServlet extends Servlet {

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
			responsePacket.addEntity("Location: /index.html");
			responsePacket.addEntity("Set-Cookie: logined=true; Path=/");
			return responsePacket;
		} catch (IllegalArgumentException e) {
			responsePacket.setHttpStatus(HttpStatus.FOUND);
			responsePacket.addEntity("Location: /user/login_failed.html");
			responsePacket.setBody(HttpStatus.NOT_FOUND.getMessage());
		} finally {
			destroy();
			return responsePacket;
		}
	}

	@Override
	public void doGet() {
		try {
			User criteria = db.findUserById(requestPacket.header.queryString.get("userId"));
			if (criteria == null) {
				throw new IllegalArgumentException("invalid userId");
			}
			if (!criteria.getPassword().equals(requestPacket.header.queryString.get("password"))) {
				throw new IllegalArgumentException("wrong userId or password");
			}
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	@Override
	public void doPost() {
		try {
			User criteria = db.findUserById(requestPacket.body.params.get("userId"));
			if (criteria == null) {
				throw new IllegalArgumentException("invalid userId");
			}
			if (!criteria.getPassword().equals(requestPacket.body.params.get("password"))) {
				throw new IllegalArgumentException("wrong userId or password");
			}
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	@Override
	public void destroy() {
	}
}
