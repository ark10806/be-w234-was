package webserver.service;

import http.RequestPacket;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

//
public class Backend {
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String rootDir = "./webapp/";
    private Map<String, Runnable> router = new HashMap<>();
    private HttpStatus httpStatus;
    private String view = "404 resource not found";
    private RequestPacket requestPacket;
    private List<String> responseEntity = new ArrayList<>();


    public Backend(RequestPacket requestPacket) {
        router.put("/user/create", () -> userCreate());
        router.put("/user/login", () -> userLogin());
        this.requestPacket = requestPacket;
    }

    public String route() {
        try {
            this.view = routeView(requestPacket.header.url);
            if (router.containsKey(requestPacket.header.url)) {
                router.get(requestPacket.header.url).run();
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            return this.view;
        }
    }

    private String routeView(String url) {
        try {
            this.httpStatus = HttpStatus.OK;
            return Files.readString(new File(rootDir + url).toPath());
        } catch (IOException e) {
            logger.debug("routeView: {}", e.getMessage());
            this.httpStatus = HttpStatus.NOT_FOUND;
            return "404 not found";
        }
    }

    public int getHttpStatusCode() {
        return httpStatus.getStatusCode();
    }

    public String getHttpStatusMessage() {
        return httpStatus.getMessage();
    }

    public List<String> getResponseEntity() {
        return this.responseEntity;
    }


    private void userCreate() {
        try {
            UserCreateServlet userCreateServlet = new UserCreateServlet();
            userCreateServlet.init(requestPacket);
            this.httpStatus = HttpStatus.FOUND;
            this.responseEntity.add("Location: /user/login.html");
        } catch (IllegalArgumentException e) {
            this.view = e.getMessage();
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
