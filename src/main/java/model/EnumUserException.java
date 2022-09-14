package model;

public enum EnumUserException {
    INVALID_USER_ID("userId", "invalid user id"),
    INVALID_PASSWORD("password", "invalid password"),
    INVALID_NAME("name", "invalid name"),
    INVALID_EMAIL("email", "invalid email"),
    VALID_ARGS("args", "valid");

    private String key;
    private String message;

    EnumUserException(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}
