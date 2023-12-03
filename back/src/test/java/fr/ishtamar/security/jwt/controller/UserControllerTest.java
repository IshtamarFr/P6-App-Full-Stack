package fr.ishtamar.security.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ishtamar.security.jwt.dto.ModifyUserRequest;
import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.repository.UserInfoRepository;
import fr.ishtamar.security.jwt.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    JwtService jwtService;
    @MockBean
    UserInfoRepository userInfoRepository;

    final ObjectMapper mapper=new ObjectMapper();

    @Test
    @WithMockUser(roles="USER")
    public void ChangeAuthMe() throws Exception {
        UserInfo mockUser=UserInfo.builder()
                .name("Ishta")
                .email("test@test.com")
                .password("123456")
                .build();

        ModifyUserRequest mockUserChange=ModifyUserRequest.builder()
                .name("Ishta")
                .email("test@test.com")
                .build();

        when(jwtService.extractUsername("123456789123456789")).thenReturn("test@test.com");
        when(userInfoRepository.findByEmail("test@test.com")).thenReturn(Optional.of(mockUser));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/auth/me")
                .header("Authorization","Bearer 123456789123456789")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUserChange))
        ).andExpect(status().isOk());
        verify(jwtService,times(2)).extractUsername("123456789123456789");
    }
}
