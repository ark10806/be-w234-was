package webserver.http;

public enum HttpStatusCode {
    OK(200), NOT_FOUND(404);

    private final int statusCode;

    HttpStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
