// package db;
//
// import com.google.common.collect.Maps;
//
// import java.util.Map;
//
// import javax.management.openmbean.KeyAlreadyExistsException;
//
// public class Database {
//
//     private static Map<String, User> users = Maps.newHashMap();
//
//     public static void addUser(User user) throws KeyAlreadyExistsException {
//         if (users.containsKey(user.getUserId()))
//             throw new KeyAlreadyExistsException();
//         users.put(user.getUserId(), user);
//     }
//
//     public static User findUserById(String userId) {
//         return users.get(userId);
//     }
//
//
// }
