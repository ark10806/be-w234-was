package webserver.repository;

import model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserMemoryRepostiory implements UserRepository {
    private static Map<Integer, User> users = new ConcurrentHashMap<>();
    private static int sequence = 0;

    @Override
    public User save(User user) {
        if (users.containsValue(user)) {
            return null;
        }

        users.put(sequence++, user);
        return user;
    }

    @Override
    public User findByUserId(String id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }
}
