package model;

import exception.CreateUserException;
import exception.EnumUserException;
import model.validator.EmailValidator;
import model.validator.NameValidator;
import model.validator.PasswordValidator;
import model.validator.UserIdValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.Map;

public class User {
    private static final Logger logger = LoggerFactory.getLogger(User.class);
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

    /**
     * @param url 회원가입 버튼 클릭 시 전달되는 url (ex. /create?userId=~~~)
     * @return User object
     */
    public static User createUser(String url) throws CreateUserException {
        int index = url.indexOf("?");
        String queryString = url.substring(index + 1);
        Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
        EnumUserException enumUserException = User.isValid(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        if ( enumUserException != EnumUserException.VALID_ARGS ) {
            throw new CreateUserException(enumUserException.getMessage());
        }
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        logger.debug("{}", user);
        return user;
    }

    /**
     * @param userId    {@link model.validator.UserIdValidator}
     * @param password  {@link model.validator.PasswordValidator}
     * @param name      {@link model.validator.NameValidator}
     * @param email     {@link model.validator.EmailValidator}
     * @return
     */
    public static EnumUserException isValid(String userId, String password, String name, String email) {
        if (UserIdValidator.isValid(userId) == false) {
            return EnumUserException.INVALID_USER_ID;
        }
        if (PasswordValidator.isValid(password) == false) {
            return EnumUserException.INVALID_PASSWORD;
        }
        if (NameValidator.isValid(name) == false) {
            return EnumUserException.INVALID_NAME;
        }
        if (EmailValidator.isValid(email) == false) {
            return EnumUserException.INVALID_EMAIL;
        }
        return EnumUserException.VALID_ARGS;
    }
}
