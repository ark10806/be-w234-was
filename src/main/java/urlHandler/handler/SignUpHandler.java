package urlHandler.handler;


import model.User;
import webserver.RequestParser;

import java.util.Map;

public class SignUpHandler implements UrlHandler {

    RequestParser requestParser = new RequestParser();

    @Override
    public byte[] handle(String url) {
        Map<String, String> params = requestParser.getParams(url);
        User user = createUser(params);
        return user.toString().getBytes();
    }

    private User createUser(Map<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        String name = params.get("name");
        String email = params.get("email");
        return new User(userId, password, name, email);
    }
}
