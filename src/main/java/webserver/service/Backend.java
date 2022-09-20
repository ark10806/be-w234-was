package webserver.service;

import http.RequestPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Backend {
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String rootDir = "./webapp/";
    private Map<String, Runnable> path = new HashMap<>();
    private Map<String, String> params;
    private String method;
    private HttpStatus httpStatus;
    private byte[] view = "404 resource not found".getBytes();
    private RequestPacket requestPacket;
    private List<String> responseEntity = new ArrayList<>();


    public Backend(RequestPacket requestPacket) {
        // responseEntity.put("Content-Type", requestPacket.header.entity.get("Accept:"));
        path.put("/user/create", () -> userCreate());
        path.put("/user/login", () -> userLogin());
        this.requestPacket = requestPacket;
    }

    public byte[] route() {
        try {
            this.view = routeView(requestPacket.header.url);
            this.method = requestPacket.header.method;
            if (path.containsKey(requestPacket.header.url)) {
                path.get(requestPacket.header.url).run();
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            return this.view;
        }
    }

    private byte[] routeView(String url) {
        try {
            this.httpStatus = HttpStatus.OK;
            return Files.readAllBytes(new File(rootDir + url).toPath());
        } catch (IOException e) {
            logger.debug("routeView: {}", e.getMessage());
            this.httpStatus = HttpStatus.NOT_FOUND;
            return "404 not found".getBytes();
        }
    }

    public int getHttpStatusCode() {
        return httpStatus.getStatusCode();
    }

    public String getHttpStatusMessage() {
        return httpStatus.getMessage();
    }
    public List<String> getResponseEntity() { return this.responseEntity; }


    private void userCreate() {
        try {
            UserCreateServlet userCreateServlet = new UserCreateServlet();
            userCreateServlet.init(requestPacket);
            this.httpStatus = HttpStatus.FOUND;
            this.responseEntity.add("Location: /user/login.html");
        } catch (IllegalArgumentException e) {
            this.view = e.getMessage().getBytes();
            this.httpStatus = HttpStatus.BAD_REQUEST;
        }
    }

    private void userLogin() {
        try {
           UserLoginServlet userLoginServlet = new UserLoginServlet();
           userLoginServlet.init(requestPacket);
           this.httpStatus = HttpStatus.FOUND;
           this.responseEntity.add("Location: /index.html");
           this.responseEntity.add("Set-Cookie: logined=true; Path=/");
        } catch (IllegalArgumentException e) {
            this.view = routeView("/user/login_failed.html");
            this.httpStatus = HttpStatus.UNAUTHORIZED;
        }
    }
}
