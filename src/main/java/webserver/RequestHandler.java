package webserver;

import java.io.*;
import java.net.Socket;

import db.Database;
import exception.CreateUserException;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            if (line == null) {
                return;
            }

            String url = HttpRequestUtils.getUrl(line);
            url = parseUrl(url);
            HttpResponse httpResponse = new HttpResponse(out, url, "200");
            httpResponse.response();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String parseUrl(String url) {
        if (url.startsWith("/create?")) {
            try {
                Database.addUser(User.createUser(url));
            } catch (CreateUserException e) {
                logger.debug("CreateUserException : {}", e.getMessage());
            } finally {
                url = "/index.html"; // 회원가입 버튼 클릭 후, "index.html"으로 페이지 이동
            }
        }
        return url;
    }
}
