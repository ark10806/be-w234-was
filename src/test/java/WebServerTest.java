import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.RequestParser;

public class WebServerTest {

    @Test
    @DisplayName("request message로부터 url을 추출할 수 있다")
    void getUrl() {
        //given
        String startLine = "GET /index.html HTTP/1.1";

        //when
        RequestParser rp = new RequestParser();
        String result = rp.getUrl(startLine);

        //then
        Assertions.assertThat(result).isEqualTo("/index.html");
    }


}
