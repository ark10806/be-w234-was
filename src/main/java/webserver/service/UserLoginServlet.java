package webserver.service;

import db.Database;
import http.RequestPacket;
import model.User;

public class UserLoginServlet implements Servlet {
	private Database db = new Database();
	private RequestPacket requestPacket;

	@Override
	public void init(RequestPacket requestPacket) {
		this.requestPacket = requestPacket;
		try {
			if ("GET".equals(requestPacket.header.method)) {
				doGet();
			}
			if ("POST".equals(requestPacket.header.method)) {
				doPost();
			}
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	@Override
	public void doGet() {
		try {
			User criteria = db.findUserById(requestPacket.header.params.get("userId"));
			if (criteria == null) {
				throw new IllegalArgumentException("invalid userId");
			}
			if (!criteria.getPassword().equals(requestPacket.header.params.get("password"))) {
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
