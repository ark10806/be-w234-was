package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.RequestHandler;
import webserver.service.Backend;

public class ResponsePacket {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private DataOutputStream dos;

	public ResponsePacket(final OutputStream out) {
		dos = new DataOutputStream(out);
	}

	public void prn() {
	}

	public void flush() {
		try {
			dos.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void write(Backend backend, String contentType, byte[] body) {
		try{
			dos.writeBytes(String.format("HTTP/1.1 %s %s \r\n",
				backend.getHttpStatusCode(), backend.getHttpStatusMessage()));
			dos.writeBytes(String.format("Content-Type: %s  \r\n", contentType));
			dos.writeBytes("Content-Length: " + body.length + "\r\n");
			for (String line : backend.getResponseEntity()) {
				dos.writeBytes(line + "\r\n");
			}
			dos.writeBytes("\r\n");
			dos.write(body, 0, body.length);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
