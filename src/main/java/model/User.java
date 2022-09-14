package model;

import model.validator.EmailValidator;
import model.validator.NameValidator;
import model.validator.PasswordValidator;
import model.validator.UserIdValidator;

public class User {
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
