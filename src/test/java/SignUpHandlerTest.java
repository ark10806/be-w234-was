import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import urlHandler.handler.SignUpHandler;

import java.io.IOException;

public class SignUpHandlerTest {

    SignUpHandler signUpHandler;

    @BeforeEach
    void beforeEach() {
       signUpHandler = new SignUpHandler();
    }

    @Test
    @DisplayName("회원가입 시 회원 정보가 User 클래스에 저장되어야 한다")
    void signup() throws IOException {
        //given
        String url = "/user/create?userId=testId&password=password&name=taki&email=taki@abcd.com";

        //when
        byte[] sut = signUpHandler.handle(url);

        //then
        User user = new User("testId", "password", "taki", "taki@abcd.com");
        Assertions.assertThat(new String(sut)).isEqualTo(user.toString());
    }
}
