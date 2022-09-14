package webserver.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceHandlerMapper {
    private static final Map<String, ServiceHandler> handlers;

    static {
        handlers = new HashMap<>();

        handlers.put("static file", new StaticFileServiceHandler());
    }

    private ServiceHandlerMapper() {
    }

    public static ServiceHandler getHandler(String url) {
        if (handlers.containsKey(url)) {
            return handlers.get(url);
        } else {
            return handlers.get("static file");
        }
    }
}
