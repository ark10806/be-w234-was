package exception;

public enum UserExceptionMessage {
  INVALID_USER_PARAMETERS("Invalid Parameters"),
  USER_ALREADY_EXISTS("User Already Exists"),
  USER_NOT_FOUND("User Not Found");

  final String message;
  UserExceptionMessage(String message) {
    this.message = message;
  }
}
