package db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User implements Comparable<User> {

  public User() {}

  public User(String userId, String password, String name, String email) {
    if (userId != null && password != null && name != null && email != null) {
      this.userId = userId;
      this.password = password;
      this.name = name;
      this.email = email;
    } else {
      throw new IllegalArgumentException(this.toString() + " are not valid arguments.");
    }
  }

  @Id
  @Column(nullable = false, name = "user_id")
  private String userId;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Override
  public int compareTo(User u) {
    return this.userId.compareTo(u.userId);
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", password=" + password
      + ", name=" + name + ", email=" + email + "]";
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
}
