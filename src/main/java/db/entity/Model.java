package db.entity;

import java.util.List;

public interface Model {
  void save(User user);

  User findById(String userId);

  void deleteAll();

  List<User> findAll();

  void closeEntityManager();

  void createEntityManager();
}
