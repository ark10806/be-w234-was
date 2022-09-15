package webserver;

public class RequestParser {
    public static String getUrl(String firstLine) {
        String[] splitted = firstLine.split(" ");
        String url = splitted[1];
        return url;
    }
}
