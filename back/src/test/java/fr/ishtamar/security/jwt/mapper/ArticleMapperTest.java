package fr.ishtamar.security.jwt.mapper;

import fr.ishtamar.security.jwt.dto.ArticleDto;
import fr.ishtamar.security.jwt.entity.Article;
import fr.ishtamar.security.jwt.entity.Topic;
import fr.ishtamar.security.jwt.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ArticleMapperTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void testArticleToDto() {
        UserInfo user= UserInfo.builder()
                .id(106L)
                .name("TheOldMan")
                .email("106@scp.com")
                .build();

        Topic topic=Topic.builder()
                .id(1L)
                .name("Interesting Subject")
                .build();

        Article article = Article.builder()
                .id(1L)
                .title("mockTitle")
                .content("mockContent")
                .author(user)
                .topic(topic)
                .build();

        ArticleDto articleDto = articleMapper.toDto(article);

        assertThat(articleDto.getContent()).isEqualTo("mockContent");
        assertThat(articleDto.getAuthor_id()).isEqualTo(106L);
        assertThat(articleDto.getTopic_id()).isEqualTo(1L);
    }
}
