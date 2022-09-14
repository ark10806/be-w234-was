package webserver.repository;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserMemoryRepository implements UserRepository {
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
    public List<User> findAll() {
        List<User> list = new ArrayList<>();

        for (User user : users.values()) {
            list.add(user);
        }

        return list;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public void clear() {
        users.clear();
    }
}
