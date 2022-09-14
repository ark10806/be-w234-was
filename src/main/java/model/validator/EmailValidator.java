package model.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    /** account
     * 대소문자, 숫자
     * 특수문자는 dot(.), underscore(_), hyphen(-)만 허용
     * dot(.)은 시작과 끝에 오거나 연속될 수 없음
     * 64 이하 길이
     */
    /** domain
     * 대소문자, 숫자
     * dot(.), hyphen(-)은 시작과 끝에 오거나 연속될 수 없음
     * top level domain (tld) 길이 2 이상
     */
    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
