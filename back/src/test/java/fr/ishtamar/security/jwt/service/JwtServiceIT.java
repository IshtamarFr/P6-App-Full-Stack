package fr.ishtamar.security.jwt.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtServiceIT {
    @Mock
    UserDetails userDetails;
    @Autowired
    JwtService jwtService;

    @Test
    public void testGenerateTokenIsSetUp() {
        String jwt=jwtService.generateToken("test@test.com");
        assertThat(jwt).isNotEmpty();
    }

    @Test
    public void testGenerateTokenIsValid() {
        when(userDetails.getUsername()).thenReturn("test@test.com");
        String jwt = jwtService.generateToken("test@test.com");
        assertThat(jwtService.validateToken(jwt,userDetails)).isTrue();
    }

    @Test
    public void testGenerateTokenIsNotValid() {
        when(userDetails.getUsername()).thenReturn("test123456@test.com");
        String jwt = jwtService.generateToken("test@test.com");
        assertThat(jwtService.validateToken(jwt,userDetails)).isFalse();
    }
}
