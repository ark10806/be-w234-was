package webserver;

public class Response {
    private final Protocol protocol;
    private final StatusCode statusCode;
    private final ContentType contentType;
    private final int contentLength;
    private final byte[] body;

    private Response(Protocol protocol, StatusCode statusCode, ContentType contentType, byte[] body) {
        this.protocol = protocol;
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.body = body;
        this.contentLength = body.length;
    }

    public static Response ok(Protocol protocol, ContentType contentType, byte[] body) {
        return new Response(protocol, StatusCode.OK, contentType, body);
    }

    public static Response badRequest(Protocol protocol, String message) {
        return new Response(protocol, StatusCode.BAD_REQUEST, ContentType.TEXT_HTML, message.getBytes());
    }

    public static Response notFound(Protocol protocol) {
        return new Response(protocol, StatusCode.NOT_FOUND, ContentType.TEXT_HTML, "NOT FOUND PAGE".getBytes());
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public byte[] getBody() {
        return body;
    }
}
