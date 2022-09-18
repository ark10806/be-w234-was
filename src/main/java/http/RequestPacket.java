package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class RequestPacket {
    public ReqHeader header = new ReqHeader();
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
            while (!line.equals("")) {
                lineitem = line.split(" ");
                this.header.entity.put(lineitem[0], lineitem[1]);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseBody() {
        try {
            if (br.ready()) {
                line = br.readLine();
                while (line != null) {
                    this.body.append("line\n");
                    br.readLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
