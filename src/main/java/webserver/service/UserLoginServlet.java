package webserver.service;

import db.Database;
import http.ResponsePacket;
import model.Session;
import model.User;

public class UserLoginServlet extends Servlet {
	String uid;

	public UserLoginServlet(Database db, Session session) {
		super(db, session);
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
			responsePacket.setHttpStatus(HttpStatus.FOUND);
			responsePacket.addEntity("Location: /index.html");
			responsePacket.addEntity(String.format("Set-Cookie: logined=%s; Path=/",
				uid));
			sessions.put(uid);
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
		this.uid = requestPacket.header.queryString.get("userId");
		try {
			User criteria = db.findUserById(this.uid);
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
		this.uid = requestPacket.body.params.get("userId");
		try {
			User criteria = db.findUserById(this.uid);
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
