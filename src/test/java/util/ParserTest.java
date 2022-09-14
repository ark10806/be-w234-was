package util;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static util.Parser.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
public class ParserTest {

    @Test
    @DisplayName("String split 할 때, String이 나누어진 개수와 입력한 숫자의 개수가 다를때 error 발생 ")
    void splitCheckErrorTest(){
        //Assertions.assertThrows(NotFoundUrlException.class,()->urlFromRequestLine("get"));
        assertThatThrownBy(()-> StringDivideAndCheckNum("GET /index.html http/1.1"," ",2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Split string and CheckNumber do not match");
    }

    @Test
    @DisplayName("String split 할 때 String이 나누어진 개수와 입력한 숫자가 같음 ")
    void splitTest(){
        //Assertions.assertThrows(NotFoundUrlException.class,()->urlFromRequestLine("get"));
        assertThat(StringDivideAndCheckNum("GET /index.html http1.1"," ",3)[1])
                .isEqualTo("/index.html");

    }

}
