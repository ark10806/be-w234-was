package webserver.service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.RequestHandler;
import webserver.service.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;

public class Backend {
    private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final String rootDir = "./webapp/";
    private Map<String, Runnable> path = new HashMap<>();
    private Map<String, String> params;
    private Database db = new Database();
    private HttpStatus httpStatus;
    private byte[] view = "404 resource not found".getBytes();


    public Backend() {
        path.put("/user/create", () -> userCreate());
    }

    public byte[] route(final String method, final String url, final Map<String, String> params) {
        try {
            this.view = routeView(url);
            if (path.containsKey(url)) {
                setParams(params);
                path.get(url).run();
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

    private void setParams(Map<String, String> params) {
        this.params = params;
    }

    private void userCreate() {
        db.addUser(new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        ));
        this.view = routeView("/index.html");
        this.httpStatus = HttpStatus.CREATED;
    }
}
