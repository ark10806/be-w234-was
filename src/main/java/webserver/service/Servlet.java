package webserver.service;

import java.util.Map;

import http.RequestPacket;

public interface Servlet {
	void init(RequestPacket requestPacket);
	void doGet();
	void doPost();
	void destroy();
}
