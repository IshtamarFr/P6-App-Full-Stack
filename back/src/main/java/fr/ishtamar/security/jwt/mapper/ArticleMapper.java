package fr.ishtamar.security.jwt.mapper;

import fr.ishtamar.security.jwt.dto.ArticleDto;
import fr.ishtamar.security.jwt.entity.Article;
import fr.ishtamar.security.jwt.service.TopicService;
import fr.ishtamar.security.jwt.service.UserInfoService;
import fr.ishtamar.security.jwt.service.impl.TopicServiceImpl;
import fr.ishtamar.security.jwt.service.impl.UserInfoServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "Spring")
public abstract class ArticleMapper implements EntityMapper<ArticleDto, Article> {

    @Autowired
    UserInfoService userInfoService=new UserInfoServiceImpl();

    @Autowired
    TopicService topicService= new TopicServiceImpl();

    @Mappings({
            @Mapping(target="author", expression="java(this.userInfoService.getUserById(articleDto.getAuthor_id()))"),
            @Mapping(target="topic", expression="java(this.topicService.getTopicById(articleDto.getTopic_id()))")
    })
    public abstract Article toEntity(ArticleDto articleDto);

    @Mappings({
            @Mapping(source= "article.author.id",target="author_id"),
            @Mapping(source= "article.topic.id",target="topic_id"),
            @Mapping(source= "article.author.name",target="author_name"),
            @Mapping(source= "article.topic.name",target="topic_name")
    })
    public abstract ArticleDto toDto(Article article);
}
