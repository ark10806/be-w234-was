import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import urlHandler.handler.StaticHtmlHandler;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class StaticHtmlHandlerTest {

    StaticHtmlHandler staticHtmlHandler;

    @BeforeEach
    void beforeEach() {
        staticHtmlHandler = new StaticHtmlHandler();
    }

    @Test
    @DisplayName("/index.html 접속 시 webapp/index.html 파일을 반환해야 한다")
    void test() throws IOException {
        //given
        String url = "/index.html";

        //when
        byte[] sut = staticHtmlHandler.handle(url);

        //then
        assertThat(sut).isNotEmpty();
    }
}
