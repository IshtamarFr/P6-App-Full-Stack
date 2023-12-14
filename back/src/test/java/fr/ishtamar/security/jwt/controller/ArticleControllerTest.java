package fr.ishtamar.security.jwt.controller;

import fr.ishtamar.security.jwt.entity.Article;
import fr.ishtamar.security.jwt.entity.Comment;
import fr.ishtamar.security.jwt.repository.ArticleRepository;
import fr.ishtamar.security.jwt.repository.CommentRepository;
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

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ArticleRepository articleRepository;
    @MockBean
    CommentRepository commentRepository;
    @MockBean
    JwtService jwtService;

    Article mockArticle;
    Comment mockComment1;
    Comment mockComment2;
    List<Comment> mockComments;

    @BeforeEach
    void initTest() {
        mockArticle=Article.builder()
                .id(999L)
                .content("I love mocks")
                .build();

        mockComment1=Comment.builder()
                .id(1L)
                .article(mockArticle)
                .content("Mock comment for article 999")
                .build();

        mockComment2=Comment.builder()
                .id(2L)
                .article(mockArticle)
                .content("Another comment for article 999")
                .build();

        mockComments=List.of(mockComment1,mockComment2);
    }

    @Test
    public void testGetArticleWithoutAuthorized() throws Exception {
        mockMvc.perform(get("/article/42"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testGetArticleById() throws Exception {
        when(articleRepository.findById(42L)).thenReturn(Optional.of(mockArticle));

        mockMvc.perform(get("/article/42"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("mock")));
    }

    @Test
    @WithMockUser(roles="USER")
    public void testGetAllCommentsForArticle42() throws Exception {
        when(commentRepository.findAllWithArticleId(42L)).thenReturn(mockComments);

        mockMvc.perform(get("/article/42/comments"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Mock")))
                .andExpect(content().string(containsString("Another")));
    }

    @Test
    @WithMockUser(roles="USER")
    public void testCreateNewCommentForValidArticle() throws Exception {
        when(articleRepository.findById(42L)).thenReturn(Optional.of(mockArticle));
        when(jwtService.extractUsername("123456789123456789")).thenReturn("test@test.com");

        mockMvc.perform(post("/article/42/comments")
                        .header("Authorization","Bearer 123456789123456789")
                        .contentType(MediaType.APPLICATION_JSON).content("I love candies")
                ).andExpect(status().isOk());
        verify(jwtService,times(2)).extractUsername("123456789123456789");
        verify(commentRepository, times(1)).save(any());
    }
}
