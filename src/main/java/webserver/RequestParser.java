package webserver;

public class RequestParser {

    public String getUrl(String startLine) {
        String[] tokens = startLine.split(" ");
        return tokens[1];
    }


}
