package webserver.service;

import db.Database;
import http.RequestPacket;
import model.User;

public class UserCreateServlet implements Servlet {
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
		} finally {
			destroy();
		}
	}
	@Override
	public void doGet() {
		try {
			db.addUser(new User(
				requestPacket.header.params.get("userId"),
				requestPacket.header.params.get("password"),
				requestPacket.header.params.get("name"),
				requestPacket.header.params.get("email")
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
