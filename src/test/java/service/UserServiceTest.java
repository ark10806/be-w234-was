package service;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static util.Parser.StringDivideAndCheckNum;

public class UserServiceTest {


    @Test
    @DisplayName("동일한 Id의 유저 가입실패 테스트")
    void UserServiceTest(){
        UserService userService = new UserService();

        User user1 = new User("user1","pw1","name1","email");
        User user2 = new User("user1","pw2","name2","email2");

        userService.SignUp(user1);

        assertThatThrownBy(()-> userService.SignUp(user1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("This userId already exists");
    }
}
