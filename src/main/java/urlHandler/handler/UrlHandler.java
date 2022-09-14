package urlHandler.handler;

import java.io.IOException;

public interface UrlHandler {

    byte[] handle(String url) throws IOException;
}
