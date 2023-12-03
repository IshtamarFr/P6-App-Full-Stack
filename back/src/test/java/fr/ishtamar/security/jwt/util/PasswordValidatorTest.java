package fr.ishtamar.security.jwt.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PasswordValidatorTest {
    @Autowired
    PasswordValidator passwordValidator;

    @Test
    public void testValidPassword() {
        assertThat(passwordValidator.isValid("1aA7527azE*t")).isEqualTo(true);
    }

    @Test
    public void testNonValidPasswords() {
        //Too short
        assertThat(passwordValidator.isValid("Aa1")).isEqualTo(false);
        //Too long
        assertThat(passwordValidator.isValid(
                "Aa1*zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz")
        ).isEqualTo(false);
        //Missing A
        assertThat(passwordValidator.isValid("ba279358z*")).isEqualTo(false);
        //Missing a
        assertThat(passwordValidator.isValid("728965214ABCD")).isEqualTo(false);
        //Missing 1
        assertThat(passwordValidator.isValid("ABCDabcd")).isEqualTo(false);
    }
}
