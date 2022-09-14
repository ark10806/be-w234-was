package webserver;

import webserver.service.ServiceHandlerMapper;

public class Servlet {
    public String service(RequestParser requestParser) {
        try {
            return ServiceHandlerMapper.getHandler(requestParser.path).handle(requestParser);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
