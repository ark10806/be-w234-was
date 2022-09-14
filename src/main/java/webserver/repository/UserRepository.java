package webserver.repository;

import model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    User findByUserId(String id);

    List<User> findAll();

    User update(User user);

    boolean delete(User user);

    void clear();
}
