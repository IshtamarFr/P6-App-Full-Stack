package fr.ishtamar.security.jwt.mapper;

import fr.ishtamar.security.jwt.dto.ArticleDto;
import fr.ishtamar.security.jwt.entity.Article;
import fr.ishtamar.security.jwt.entity.Topic;
import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.service.impl.TopicServiceImpl;
import fr.ishtamar.security.jwt.service.impl.UserInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArticleMapperIT {
    @Autowired
    private ArticleMapper articleMapper;

    @MockBean
    private UserInfoServiceImpl userInfoService;

    @MockBean
    private TopicServiceImpl topicService;

    @Test
    public void testArticleToEntity() {
        UserInfo user= UserInfo.builder()
                .id(106L)
                .name("TheOldMan")
                .email("106@scp.com")
                .build();

        Topic topic=Topic.builder()
                .id(1L)
                .name("Interesting Subject")
                .build();

        ArticleDto articleDto = ArticleDto.builder()
                .id(42L)
                .title("mockTitle")
                .content("mockContentDto")
                .author_id(106L)
                .topic_id(1L)
                .created_at(new Date())
                .build();

        when(userInfoService.getUserById(106L)).thenReturn(user);
        when(topicService.getTopicById(1L)).thenReturn(topic);

        Article article = articleMapper.toEntity(articleDto);
        assertThat(article.getContent()).isEqualTo("mockContentDto");
        assertThat(article.getAuthor().getName()).isEqualTo("TheOldMan");
        assertThat(article.getTopic().getName()).isEqualTo("Interesting Subject");
    }
}
