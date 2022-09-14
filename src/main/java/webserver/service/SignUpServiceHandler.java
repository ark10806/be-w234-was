package webserver.service;

import model.User;
import webserver.RequestParser;

public class SignUpServiceHandler implements ServiceHandler {
    @Override
    public String handle(RequestParser requestParser) {
        User user = new User(requestParser.params.get("userId"), requestParser.params.get("password"), requestParser.params.get("name"), requestParser.params.get("email"));
        return user.toString();
    }
}
