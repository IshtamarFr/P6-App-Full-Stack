package fr.ishtamar.security.jwt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ishtamar.security.jwt.payload.AuthRequest;
import fr.ishtamar.security.jwt.payload.ModifyUserRequest;
import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.service.JwtService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    JwtService jwtService;

    final ObjectMapper mapper=new ObjectMapper();

    @Test
    public void testWelcomeIsOk() throws Exception {
        this.mockMvc.perform(get("/auth/welcome")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome")));
    }

    @Test
    public void testAddNewUserEmailIsAlreadyUsed() throws Exception {
        UserInfo mockUser=UserInfo.builder()
                .name("Ishta")
                .email("test@test.com")
                .password("Aa123456")
                .roles("ROLE_USER")
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin() throws Exception {
        AuthRequest mockRequest=AuthRequest.builder()
                .email("test@test.com")
                .password("Aa123456")
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testAuthMe() throws Exception {
        when(jwtService.extractUsername("123456789123456789")).thenReturn("test@test.com");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/auth/me")
                        .header("Authorization","Bearer 123456789123456789"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ishta")));
        verify(jwtService,times(2)).extractUsername("123456789123456789");
    }

    @Test
    @WithMockUser(roles="USER")
    public void testInvalidAuthMe() throws Exception {
        when(jwtService.extractUsername("123456789123456789")).thenReturn("oops@oops.com");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/auth/me")
                        .header("Authorization","Bearer 123456789123456789")
                ).andExpect(status().isNotFound());
        verify(jwtService,times(2)).extractUsername("123456789123456789");
    }

    @Test
    @WithMockUser(roles="USER")
    public void ChangeAuthMe() throws Exception {
        when(jwtService.extractUsername("123456789123456789")).thenReturn("test@test.com");

        ModifyUserRequest mockUserChange=ModifyUserRequest.builder()
                .name("Ishta")
                .email("test@test.com")
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/auth/me")
                        .header("Authorization","Bearer 123456789123456789")
                    .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockUserChange))
                ).andExpect(status().isOk());
        verify(jwtService,times(2)).extractUsername("123456789123456789");
    }

    @Test
    @WithMockUser(roles="USER")
    public void TestGetUserById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ishta")))
                .andExpect(content().string(CoreMatchers.not(containsString("$2"))));
    }
}
