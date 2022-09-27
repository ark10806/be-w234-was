package model;

public class User implements Comparable<User> {
  private String userId;
  private String password;
  private String name;
  private String email;

  public User(String userId, String password, String name, String email) {
    if (userId != null && password != null && name != null && email != null) {
      this.userId = userId;
      this.password = password;
      this.name = name;
      this.email = email;
    } else {
      throw new IllegalArgumentException(toString() + " are not valid arguments.");
    }
  }

  @Override
  public int compareTo(User u) {
    return this.userId.compareTo(u.userId);
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
    return "User [userId=" + userId + ", password=" + password
      + ", name=" + name + ", email=" + email + "]";
  }
}
