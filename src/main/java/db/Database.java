package db;

import com.google.common.collect.Maps;

import model.User;

import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.management.openmbean.KeyAlreadyExistsException;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) throws KeyAlreadyExistsException {
        if (users.containsKey(user.getUserId()))
            throw new KeyAlreadyExistsException();
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

}
