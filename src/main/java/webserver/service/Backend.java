package webserver.service;

import http.RequestPacket;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.ResponsePacket;
import webserver.RequestHandler;

public class Backend {
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Map<String, Servlet> router = new HashMap<>();
    String rootDir = "./webapp/";
    private RequestPacket requestPacket;
    private ResponsePacket responsePacket;


    public Backend(RequestPacket requestPacket, ResponsePacket responsePacket) {
        router.put("/user/create", new UserCreateServlet());
        router.put("/user/login", new UserLoginServlet());
        this.requestPacket = requestPacket;
        this.responsePacket = responsePacket;
    }

    public ResponsePacket route() {
        try {
            String url = requestPacket.header.url;
            responsePacket.setBody(routeView(url));
            responsePacket.setContentType(requestPacket.header.entity.get("Accept:"));
            if (router.containsKey(url)) {
                router.get(url).init(requestPacket, responsePacket);
                responsePacket = router.get(url).run();
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            responsePacket.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            return responsePacket;
        }
    }

    private String routeView(String url) {
        try {
            return Files.readString(new File(rootDir + url).toPath());
        } catch (IOException e) {
            logger.debug("routeView: {}", e.getMessage());
            return "404 not found";
        }
    }
}
