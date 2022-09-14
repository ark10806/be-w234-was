package webserver;

import java.io.*;
import java.net.Socket;

import http.request.HttpRequest;
import http.request.SimpleHttpRequest;
import http.response.BaseHTTPResponse;
import http.response.HttpResponse;
import http.response.ResponseHeader;
import http.response.textresponse.FileResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            HttpRequest requestData = new SimpleHttpRequest(in);
            logger.info("http request parsed");

            DataOutputStream dos = new DataOutputStream(out);

//            byte[] body = "Hello World".getBytes();
//            response200Header(dos, body.length);
//            responseBody(dos, body);

            String path = requestData.getPath();
            logger.info("PATH {}",path);
            FileResponseBody fileResponseBody = new FileResponseBody("webapp/" + path);
            logger.info("file response body created");
            HttpResponse response = new BaseHTTPResponse()
                    .status(200)
                    .addHeader(new ResponseHeader("Content-Type", "text/html;charset=utf-8"))
                    .body(fileResponseBody);
            logger.info("http response prepread");
            response.send(dos);

        } catch (IOException e) {
            logger.info("run() error");
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        try {
            connection.close();
            logger.info("Connection closed");
        } catch(Exception ee){
            logger.info("connection.close() error in run()");
            logger.error(ee.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.write("HTTP/1.1 200 OK \r\n".getBytes());
            dos.write("Content-Type: text/html;charset=utf-8\r\n".getBytes());
            dos.write(("Content-Length: " + lengthOfBodyContent + "\r\n").getBytes());
            dos.write("\r\n".getBytes());
        } catch (IOException e) {
            logger.info("response200Header() error");
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
}
