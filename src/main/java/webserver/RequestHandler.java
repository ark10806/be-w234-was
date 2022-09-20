package webserver;

import http.RequestPacket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.service.Backend;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestPacket reqPacket;
    private Backend backend;

    public RequestHandler(Socket connectionSock) {
        this.connection = connectionSock;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            reqPacket = new RequestPacket(in);
            reqPacket.prn();
            backend = new Backend(reqPacket);
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = backend.route();
            responseHeader(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 " + backend.getHttpStatusCode() + " "
                + backend.getHttpStatusMessage() + " \r\n");
            dos.writeBytes("Content-Type: " + reqPacket.header.entity.get("Accept:") + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            for (String line : backend.getResponseEntity()) {
                dos.writeBytes(line + "\r\n");
            }
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
}
