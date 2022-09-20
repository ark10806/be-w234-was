package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class RequestPacket {
    public RequestHeader header = new RequestHeader();
    public Body body = new Body();
    private BufferedReader br;
    private String line;
    private String[] lineitem;

    public void prn() {
        header.prn();
        body.prn();
    }

    public RequestPacket(final InputStream in) {
        try {
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            parse();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void parse() {
        parseGeneralHeader();
        parseSpecialHeader();
        parseEntityHeader();
        parseBody();
    }

    private void parseGeneralHeader() {

    }

    private void parseSpecialHeader() {
        try {
            lineitem = br.readLine().split(" ");
            this.header.method = lineitem[0];
            this.header.setUrl(lineitem[1]);
            this.header.httpVersion = lineitem[2];
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                br.close();;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
