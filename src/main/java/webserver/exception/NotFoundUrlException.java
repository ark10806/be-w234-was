package webserver.exception;

public class NotFoundUrlException extends RuntimeException {
    public NotFoundUrlException(String e) {
        super(e);
    }
}
