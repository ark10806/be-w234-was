package service;

import db.Database;
import model.User;

public class UserService {
    private static Database repository = Database.getDatabase();

    public void SignUp(User user){
        if (repository.findUserById(user.getUserId()) != null){
            throw new IllegalArgumentException("This userId already exists");
        }
        repository.addUser(user);
    }
}
