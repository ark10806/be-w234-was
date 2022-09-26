package webserver.servlet;

import http.RequestPacket;
import http.ResponsePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public interface ServletInterface {
	String rootDir = "./webapp/";
	Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	String routeView(String url);

	void init(RequestPacket requestPacket, ResponsePacket responsePacket);
	ResponsePacket run();

	void doGet();

	void doPost();

	void destroy();

}
