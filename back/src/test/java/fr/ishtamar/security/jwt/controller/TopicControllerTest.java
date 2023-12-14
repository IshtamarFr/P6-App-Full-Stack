package fr.ishtamar.security.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ishtamar.security.jwt.dto.ArticleDto;
import fr.ishtamar.security.jwt.entity.Article;
import fr.ishtamar.security.jwt.entity.Topic;
import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.repository.ArticleRepository;
import fr.ishtamar.security.jwt.repository.TopicRepository;
import fr.ishtamar.security.jwt.repository.UserInfoRepository;
import fr.ishtamar.security.jwt.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TopicControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    TopicRepository topicRepository;
    @MockBean
    ArticleRepository articleRepository;
    @MockBean
    JwtService jwtService;
    @MockBean
    UserInfoRepository userInfoRepository;

    final ObjectMapper mapper=new ObjectMapper();

    Topic mockTopic1;
    Topic mockTopic2;
    List<Topic> mockTopics;

    Article mockArticle1;
    Article mockArticle2;
    List<Article> mockArticles;

    @BeforeEach
    void initTests() {
        mockTopic1= Topic.builder()
                .id(1L)
                .name("Angular")
                .build();

        mockTopic2= Topic.builder()
                .id(10L)
                .name("Java")
                .build();

        mockTopics= List.of(mockTopic1,mockTopic2);

        mockArticle1=Article.builder()
                .id(1L)
                .content("Lalala")
                .topic(mockTopic1)
                .build();

        mockArticle2=Article.builder()
                .id(2L)
                .content("Hohoho")
                .topic(mockTopic2)
                .build();

        mockArticles=List.of(mockArticle1,mockArticle2);
    }

    @Test
    @WithMockUser(roles="USER")
    public void TestGetAllTopics() throws Exception {

        when(topicRepository.findAll()).thenReturn(mockTopics);

        this.mockMvc.perform(get("/topic"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Angular")))
                .andExpect(content().string(containsString("Java")));
        verify(topicRepository,times(1)).findAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void testGetTopicById() throws Exception {
        when(topicRepository.findById(10L)).thenReturn(Optional.of(mockTopic2));

        this.mockMvc.perform(get("/topic/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Java")));
        verify(topicRepository,times(1)).findById(10L);
    }

    @Test
    @WithMockUser(roles="USER")
    public void testCreateArticle() throws Exception {
        UserInfo mockUser=UserInfo.builder()
                .name("Ishta")
                .email("test@test.com")
                .password("123456")
                .build();

        ArticleDto mockArticle=ArticleDto.builder()
                .content("I eat tomatoes")
                .title("tomato power")
                .build();

        when(jwtService.extractUsername("123456789123456789")).thenReturn("test@test.com");
        when(topicRepository.findById(1L)).thenReturn(Optional.of(mockTopic1));
        when(userInfoRepository.findByEmail("test@test.com")).thenReturn(Optional.of(mockUser));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/topic/1/articles")
                .header("Authorization","Bearer 123456789123456789")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(mockArticle))
        ).andExpect(status().isOk());
        verify(jwtService,times(2)).extractUsername("123456789123456789");
    }
}
