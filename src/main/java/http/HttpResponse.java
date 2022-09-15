package http;

import jdk.jfr.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    private String status;
    private String contentType;
    private byte[] body;

    public HttpResponse(OutputStream out, String url, String status) {
        this(new DataOutputStream(out), url, status, url.substring(url.lastIndexOf('.') + 1));
    }

    public HttpResponse(DataOutputStream dos, String url, String status, String contentType) {
        this.dos = dos;
        setBody(url);
        setStatus(status);
        setContentType(contentType);
    }

    public void setBody(String url) {
        try {
            body = Files.readAllBytes(new File("./webapp" + url).toPath());
        } catch (IOException e) {
            logger.error("Exception : {}", e.getMessage());
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void response() {
        try {
            response200Header();
            responseBody();
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header() throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/" + contentType + ";charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody() throws IOException {
        dos.write(body, 0, body.length);
    }
}
