package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.service.HttpStatus;

public class ResponsePacket {
  private String httpVersion;
  private int httpStatusCode = HttpStatus.OK.getStatusCode();
  private String httpStatusMessage = HttpStatus.OK.getMessage();
  private String contentType;
  private List<String> entities = new ArrayList<>();
  private byte[] body = "".getBytes();
  private DataOutputStream dos;

  public ResponsePacket(final OutputStream out) {
    dos = new DataOutputStream(out);
  }

  public void prn() {
    System.out.println("\n############################\nRes Packet:");
    System.out.println(String.format("%s %d %s", httpVersion, httpStatusCode, httpStatusMessage));
    System.out.println(String.format("Content-Type: %s", contentType));
    System.out.println(String.format("Content-Length: " + body.length));
    for (String line : entities) {
      System.out.println(line);
    }
  }

  public void setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatusCode = httpStatus.getStatusCode();
    this.httpStatusMessage = httpStatus.getMessage();
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public void addEntity(String entity) {
    this.entities.add(entity);
  }

  public void setBody(String body) {
    this.body = body.getBytes();
  }

  void writeHeader() {
    try {
      dos.writeBytes(String.format("%s %d %s \r\n",
          httpVersion, httpStatusCode, httpStatusMessage));
      dos.writeBytes(String.format("Content-Type: %s \r\n",
          contentType));
      dos.writeBytes(String.format("Content-Length: " + body.length + "\r\n"));
      for (String line : entities) {
        dos.writeBytes(line + "\r\n");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void flush() {
    try {
      writeHeader();
      dos.writeBytes("\r\n");
      if (400 <= httpStatusCode && httpStatusCode < 600) {
        setBody(httpStatusMessage);
      }
      dos.write(body, 0, body.length);

      dos.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
