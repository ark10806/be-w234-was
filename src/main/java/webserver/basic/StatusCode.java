package webserver.basic;

public enum StatusCode {
    OK(200,"OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private final int value;
    private final String description;

    StatusCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return value + " " + description;
    }

    public static StatusCode getByValue(int value) {
        for(StatusCode status : values()) {
            if(status.value == value) return status;
        }
        throw new IllegalArgumentException("Invalid status code: " + value);
    }
}
