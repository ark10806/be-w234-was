package exception;

public enum PostExceptionMessage {
  POST_NOT_FOUND("Memo Not Found");

  final String message;

  PostExceptionMessage(String message) {
    this.message = message;
  }
}
