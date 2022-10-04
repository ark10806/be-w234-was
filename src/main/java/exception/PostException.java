package exception;

public class PostException extends RuntimeException {
  public PostException(PostExceptionMessage postExceptionMessage) {
    super(postExceptionMessage.message);
  }
}

