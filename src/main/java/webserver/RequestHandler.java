package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import db.Database;
import model.EnumUserException;
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

            try {
                String url = HttpRequestUtils.getUrl(line);
                if (url.startsWith("/create?")) {
                    try {
                        Database.addUser(createUser(url));
                    } catch (IllegalArgumentException e) {
                        logger.debug("Exception : {}", e.getMessage());
                    } finally {
                        url = "/index.html"; // 회원가입 버튼 클릭 후, "index.html"으로 페이지 이동
                    }
                }

                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
                response200Header(dos, body.length, url.substring(url.lastIndexOf('.')+1));
                responseBody(dos, body);
            } catch(UnsupportedEncodingException e) {
                logger.debug("Exception : {}", e.getMessage());
                return;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String ContentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/" + ContentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * @param url 회원가입 버튼 클릭 시 전달되는 url (ex. /create?userId=~~~)
     * @return User object
     */
    private User createUser(String url) throws IllegalArgumentException {
        int index = url.indexOf("?");
        String queryString = url.substring(index + 1);
        Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
        EnumUserException enumUserException = User.isValid(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        if ( enumUserException != EnumUserException.VALID_ARGS ) {
            throw new IllegalArgumentException(enumUserException.getMessage());
        }
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        logger.debug("{}", user);
        return user;
    }
}
