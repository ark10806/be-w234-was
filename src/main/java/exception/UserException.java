package exception;

public class UserException extends RuntimeException{
  public UserException(UserExceptionMessage userException){
    super(userException.message);
  }
}

