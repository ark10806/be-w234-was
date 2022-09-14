package webserver;

import org.apache.commons.lang3.StringUtils;

public class HeaderStartLine {
    private Method method;
    private String url;
    private String protocol;

    public HeaderStartLine (String line) {

        if (StringUtils.isEmpty(line))
            throw new RequestHandlingException("Header 입력이 비어있습니다.");

        String[] tokens = line.split(" ");

        if (tokens.length != 3)
            throw new RequestHandlingException("Header 입력이 잘못되었습니다.");

        this.method = Method.valueOf(tokens[0]);
        this.url = tokens[1];
        this.protocol = tokens[2];

    }

    public Method getMethod(){
        return this.method;
    }

    public String getUrl(){
        return this.url;
    }

    public String getProtocol(){
        return this.protocol;
    }
}
