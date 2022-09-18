package webserver;

import java.io.*;
import java.net.Socket;

import http.RequestPacket;
import webserver.service.Backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestPacket reqPacket;
    private Backend backend = new Backend();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            reqPacket = new RequestPacket(in);
            reqPacket.prn();
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = getResource();
            responseHeader(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] getResource() {
        return backend.route(reqPacket.header.method, reqPacket.header.url, reqPacket.header.params);
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 " + backend.getHttpStatusCode() + " " + backend.getHttpStatusMessage() +" \r\n");
            dos.writeBytes("Content-Type: " + reqPacket.header.entity.get("Accept:") + "\r\n");
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
}
