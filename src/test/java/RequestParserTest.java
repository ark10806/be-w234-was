import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestParser;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;

public class RequestParserTest {

    RequestParser requestParser;

    @BeforeEach
    void beforeEach() {
        requestParser = new RequestParser();
    }

    @Test
    @DisplayName("request message로부터 url을 추출할 수 있어햐 한다")
    void getUrl() {
        //given
        String startLine = "GET /index.html HTTP/1.1";

        //when
        String sut = requestParser.getUrl(startLine);

        //then
        assertThat(sut).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("url로부터 path를 추출할 수 있어야 한다")
    void getPath() {
        //given
        String url = "/test?userId=testId";

        //when
        String sut = requestParser.getPath(url);

        //then
        assertThat(sut).isEqualTo("/test");
    }

    @Test
    @DisplayName("url로부터 파라미터를 추출할 수 있어야 한다")
    void getParams() {
        //given
        String url = "/test?userId=testId&password=testPw";

        //when
        Map<String, String> sut = requestParser.getParams(url);

        //then
        assertThat(sut.get("userId")).isEqualTo("testId");
        assertThat(sut.get("password")).isEqualTo("testPw");
    }

    @Test
    @DisplayName("url에 파라미터가 없는 경우, params는 null을 반환해야 한다")
    void getParams2() {
        //given
        String url = "/test";

        //when
        Map<String, String> sut = requestParser.getParams(url);

        //then
        assertThat(sut).isNull();
    }
}
