package webserver.service;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "New resource created as a result"),
    FOUND(302, "Redirection"),
    BAD_REQUEST(400, "Server cannot or will not process the request"),
    UNAUTHORIZED(401, "Unauthorized client"),
    FORBIDDEN(403, "Client does not have access rights to the content."),
    NOT_FOUND(404, "Server can not find requested resource."),
    INTERNAL_SERVER_ERROR(500, "Server does not know how to handle.");

    private int statusCode;
    private String message;

    HttpStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
