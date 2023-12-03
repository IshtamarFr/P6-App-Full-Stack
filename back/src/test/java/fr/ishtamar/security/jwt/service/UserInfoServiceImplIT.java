package fr.ishtamar.security.jwt.service;

import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.service.impl.UserInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserInfoServiceImplIT {
    @Autowired
    private UserInfoServiceImpl userInfoServiceImpl;

    @Test
    public void testLoadUserByUserName() {
        UserDetails user= userInfoServiceImpl.loadUserByUsername("test@test.com");
        assertThat(user.getUsername()).isEqualTo("test@test.com");
    }

    @Test
    public void testUser() {
        UserInfo user= userInfoServiceImpl.getUserByUsername("test@test.com");
        assertThat(user.getName()).isEqualTo("Ishta");
    }
}
