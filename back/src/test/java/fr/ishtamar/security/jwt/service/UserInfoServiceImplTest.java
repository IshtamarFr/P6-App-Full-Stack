package fr.ishtamar.security.jwt.service;

import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.exceptionhandler.InvalidPasswordException;
import fr.ishtamar.security.jwt.repository.UserInfoRepository;
import fr.ishtamar.security.jwt.service.impl.UserInfoServiceImpl;
import fr.ishtamar.security.jwt.util.PasswordValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceImplTest {
    @Mock
    UserInfoRepository userInfoRepository;
    @Mock
    PasswordEncoder encoder;
    @Mock
    PasswordValidator passwordValidator;
    @InjectMocks
    UserInfoServiceImpl userInfoServiceImpl;

    @Test
    public void testAddUserWithValidPassword() {
        when(passwordValidator.isValid("A187*zerty")).thenReturn(true);

        UserInfo mockUser=UserInfo.builder()
                .name("mockTest")
                .password("A187*zerty")
                .email("mock@testmock.com")
                .build();

        userInfoServiceImpl.createUser(mockUser);
        verify(userInfoRepository,times(1)).save(mockUser);
    }

    @Test
    public void testAddUserWithInvalidPassword() {
        when(passwordValidator.isValid("aaa")).thenReturn(false);

        UserInfo mockUser=UserInfo.builder()
                .name("mockTest")
                .password("aaa")
                .email("mock@testmock.com")
                .build();

        assertThrows(InvalidPasswordException.class,()->userInfoServiceImpl.createUser(mockUser));
    }
}
