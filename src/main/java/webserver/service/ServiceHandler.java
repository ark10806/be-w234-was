package webserver.service;

import webserver.RequestParser;

import java.io.IOException;

public interface ServiceHandler {
    String handle(RequestParser requestParser) throws IOException;
}
