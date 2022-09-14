package model.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserIdValidator {
    /**
     * 문자, 숫자로 시작 및 끝
     * 특수문자는 dot(.), underscore(_), hyphen(-)만 허용
     * 특수문자는 시작과 끝에 오거나 연속될 수 없음
     * 5 이상 20 이하 길이
     */
    private static final String USERNAME_PATTERN =
            "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

    private static final Pattern pattern = Pattern.compile(USERNAME_PATTERN);

    public static boolean isValid(final String username) {
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

}