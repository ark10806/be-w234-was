package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestPacket {
    private final Logger logger = LoggerFactory.getLogger(RequestPacket.class);
    public RequestHeader header = new RequestHeader();
    public Body body = new Body();
    private BufferedReader br;
    private String line;
    private String[] lineitem;

    public RequestPacket(InputStream in) {
        try {
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            parse();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void prn() {
        System.out.println("\n############################\nRequest Packet:");
        header.prn();
        body.prn();
    }

    private void parse() {
        try {
            parseGeneralHeader();
            parseSpecialHeader();
            parseEntityHeader();
            parseBody();
        } catch (IOException e) {
            logger.error("Error on parse(): {}", e);
        }
    }

    private void parseGeneralHeader() {

    }

    private void parseSpecialHeader() throws IOException {
        try {
            lineitem = br.readLine().split(" ");
            this.header.method = lineitem[0];
            this.header.setUrl(lineitem[1]);
            this.header.httpVersion = lineitem[2];
        } catch (IOException e) {
            throw e;
        }
    }

    private void parseEntityHeader() {
        try {
            line = br.readLine();
            while (!line.equals("") && line != null) {
                lineitem = line.split(" ");
                String[] tmp = this.header.url.split("\\.");
                if (lineitem[0].equals("Accept:") && tmp.length >= 2) {
                    String property = tmp[tmp.length - 1];
                    if (property.equals("html")) {
                        this.header.entity.put(lineitem[0], "text/html");
                    } else if (property.equals("css")) {
                        this.header.entity.put(lineitem[0], "text/css");
                    } else {
                        this.header.entity.put(lineitem[0], "*/*");
                    }
                } else if (lineitem[0].equals("Cookie:")) {
                    this.header.cookie = new Cookie(line);
                } else {
                    this.header.entity.put(lineitem[0], lineitem[1]);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseBody() {
        try {
            if (this.header.entity.containsKey("Content-Length:")) {
                int contentLen = Integer.parseInt(this.header.entity.get("Content-Length:"));
                char[] body = new char[contentLen];
                br.read(body, 0, contentLen);
                this.body.parseParams(String.copyValueOf(body));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
