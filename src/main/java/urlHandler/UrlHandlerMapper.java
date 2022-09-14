package urlHandler;

import urlHandler.handler.StaticHtmlHandler;
import urlHandler.handler.SignUpHandler;
import urlHandler.handler.UrlHandler;
import webserver.RequestParser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UrlHandlerMapper {

    RequestParser requestParser = new RequestParser();
    Map<String, UrlHandler> handlers = new HashMap<>();

    public UrlHandlerMapper() {
        // init
        handlers.put("/user/create", new SignUpHandler());
    }

    public UrlHandler HandlerMapping(String url) {
        File file = new File("./webapp" + url);
        if (file.isFile()) { //정적 파일
            return new StaticHtmlHandler();
        }

        String path = requestParser.getPath(url);
        return handlers.get(path);
    }
}
