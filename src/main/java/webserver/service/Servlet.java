package webserver.service;

import http.RequestPacket;

public interface Servlet {
	void init(RequestPacket requestPacket);
	void doGet();
	void doPost();
	void destroy();
}
