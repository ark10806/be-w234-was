package webserver;

import http.RequestPacket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.ResponsePacket;
import webserver.service.Backend;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestPacket reqPacket;
    private ResponsePacket resPacket;
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

            resPacket = new ResponsePacket(out);
            resPacket.write(
                backend,
                reqPacket.header.entity.get("Accept:"),
                backend.route().getBytes()
            );
            resPacket.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
