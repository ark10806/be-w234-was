package webserver.repository;

import model.User;

public interface UserRepository {
    User save(User user);

    User findByUserId(String id);

    User update(User user);

    boolean delete(User user);
}
