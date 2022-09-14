package webserver;

import com.google.common.base.Strings;

public class RequestStartLine {
    private final String[] tokens;

    public RequestStartLine(String readLine) {
        if (Strings.isNullOrEmpty(readLine)) {
            throw new WebServerException(WebServerErrorMessage.EMPTY_REQUEST);
        }

        String[] tokens = readLine.split(" ");

        if (tokens.length != 3) {
            throw new WebServerException(WebServerErrorMessage.INVALID_FORMAT);
        }

        this.tokens = tokens;
    }

    public String[] getTokens() {
        return tokens;
    }
}
