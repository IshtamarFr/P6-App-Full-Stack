package fr.ishtamar.security.jwt.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator {
    /*
        Password REGEX validation must happen when a password is created/changed, so rules can be changed later
        without making it impossible for legit users to login
    */
    @Value("${fr.ishtamar.security.jwt.password-regex}")
    private String PASSWORD_PATTERN;

    public boolean isValid(final String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
