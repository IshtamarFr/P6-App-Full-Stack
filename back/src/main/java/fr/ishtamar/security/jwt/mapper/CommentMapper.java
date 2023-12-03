package fr.ishtamar.security.jwt.mapper;

import fr.ishtamar.security.jwt.dto.CommentDto;
import fr.ishtamar.security.jwt.entity.Comment;
import fr.ishtamar.security.jwt.service.ArticleService;
import fr.ishtamar.security.jwt.service.UserInfoService;
import fr.ishtamar.security.jwt.service.impl.ArticleServiceImpl;
import fr.ishtamar.security.jwt.service.impl.UserInfoServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "Spring")
public abstract class CommentMapper implements EntityMapper<CommentDto, Comment> {
    @Autowired
    UserInfoService userInfoService=new UserInfoServiceImpl();

    @Autowired
    ArticleService articleService=new ArticleServiceImpl();

    @Mappings({
            @Mapping(target="author", expression="java(this.userInfoService.getUserById(commentDto.getAuthor_id()))"),
            @Mapping(target="article", expression="java(this.articleService.getArticleById(commentDto.getArticle_id()))")
    })
    public abstract Comment toEntity(CommentDto commentDto);

    @Mappings({
            @Mapping(source= "comment.author.id",target="author_id"),
            @Mapping(source= "comment.author.name",target="author_name"),
            @Mapping(source= "comment.article.id",target="article_id")
    })
    public abstract CommentDto toDto(Comment comment);
}
