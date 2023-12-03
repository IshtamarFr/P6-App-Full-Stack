package fr.ishtamar.security.jwt.controller;

import fr.ishtamar.security.jwt.dto.ArticleDto;
import fr.ishtamar.security.jwt.dto.CommentDto;
import fr.ishtamar.security.jwt.entity.Comment;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.security.jwt.mapper.ArticleMapper;
import fr.ishtamar.security.jwt.mapper.CommentMapper;
import fr.ishtamar.security.jwt.service.ArticleService;
import fr.ishtamar.security.jwt.service.CommentService;
import fr.ishtamar.security.jwt.service.JwtService;
import fr.ishtamar.security.jwt.service.UserInfoService;
import fr.ishtamar.security.jwt.service.impl.ArticleServiceImpl;
import fr.ishtamar.security.jwt.service.impl.CommentServiceImpl;
import fr.ishtamar.security.jwt.service.impl.UserInfoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private final ArticleService articleService= new ArticleServiceImpl();
    @Autowired
    private final CommentService commentService= new CommentServiceImpl();
    @Autowired
    private final UserInfoService userService= new UserInfoServiceImpl();
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Operation(summary = "Shows required article",responses={
            @ApiResponse(responseCode="200", description = "Article successfully shown"),
            @ApiResponse(responseCode="400", description = "Article Id doesn't exist"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public ArticleDto getArticleById(@PathVariable("id") final long id) throws EntityNotFoundException {
        return articleMapper.toDto(articleService.getArticleById(id));
    }

    @Operation(summary = "Shows all comments for required article",responses={
            @ApiResponse(responseCode="200", description = "Comments successfully shown"),
            @ApiResponse(responseCode="400", description = "Article Id doesn't exist"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @GetMapping("/{id}/comments")
    @Secured("ROLE_USER")
    public List<CommentDto> getCommentsForArticleWithId(@PathVariable("id") final long id) throws EntityNotFoundException {
        return commentMapper.toDto(commentService.getAllCommentsWithArticleId(id));
    }

    @Operation(summary = "Create new comment for required article",responses={
            @ApiResponse(responseCode="200", description = "Comments successfully created"),
            @ApiResponse(responseCode="400", description = "Article Id doesn't exist"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @PostMapping("/{id}/comments")
    @Secured("ROLE_USER")
    public Map<String,String> createComment(
            //@formatter:off
            @PathVariable("id") final long id,
            @RequestHeader(value="Authorization",required=false) String jwt,
            @RequestBody @NotBlank @Size(max=500) String content
            //@formatter:on
    ) throws EntityNotFoundException {
        String username=jwtService.extractUsername(jwt.substring(7));
        Comment comment=Comment.builder()
                .content(content)
                .author(userService.getUserByUsername(username))
                .article(articleService.getArticleById(id))
                .created_at(new Date())
                .build();
        commentService.saveComment(comment);
        Map<String,String> map=new HashMap<>();
        map.put("response","Comment created with success");
        return map;
    }

}
