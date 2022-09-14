package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public String getUrl(String startLine) {
        String[] tokens = startLine.split(" ");
        return tokens[1];
    }

    public String getPath(String url) {
        String[] tokens = url.split("\\?");
        return tokens[0];
    }

    public Map<String, String> getParams(String url) {
        Map<String, String> params = new HashMap<>();

        String[] tokens = url.split("\\?");

        if (tokens.length == 1) { // no params
            return null;
        }

        // queryParams에는 url의 ? 이후의 문자열이 담긴다.
        String queryParams = tokens[1];
        String[] pairs = queryParams.split("&");
        for (String pair : pairs) {
            String[] keyAndVal = pair.split("=");
            String key = keyAndVal[0];
            String val = keyAndVal[1];
            params.put(key, val);
        }
        return params;
    }
}
