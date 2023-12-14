package fr.ishtamar.security.jwt.controller;

import fr.ishtamar.security.jwt.dto.ArticleDto;
import fr.ishtamar.security.jwt.entity.Article;
import fr.ishtamar.security.jwt.entity.Topic;
import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.security.jwt.service.ArticleService;
import fr.ishtamar.security.jwt.service.JwtService;
import fr.ishtamar.security.jwt.service.TopicService;
import fr.ishtamar.security.jwt.service.UserInfoService;
import fr.ishtamar.security.jwt.service.impl.ArticleServiceImpl;
import fr.ishtamar.security.jwt.service.impl.TopicServiceImpl;
import fr.ishtamar.security.jwt.service.impl.UserInfoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private final TopicService topicService=new TopicServiceImpl();
    @Autowired
    private final ArticleService articleService= new ArticleServiceImpl();
    @Autowired
    private final UserInfoService userService=new UserInfoServiceImpl();

    @Autowired
    private JwtService jwtService;

    @Operation(summary = "gets all topics",responses={
            @ApiResponse(responseCode="200", description = "Shows all available topics"),
    })
    @GetMapping("")
    @Secured("ROLE_USER")
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @Operation(summary = "gets certain topic",responses={
            @ApiResponse(responseCode="200", description = "Shows required topic"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public Topic getTopicById(@PathVariable("id") final long id) throws EntityNotFoundException {
        return topicService.getTopicById(id);
    }

    @Operation(summary = "Create new article for required topic",responses={
            @ApiResponse(responseCode="200", description = "Article successfully created"),
            @ApiResponse(responseCode="400", description = "Topic Id doesn't exist"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @PostMapping("/{id}/articles")
    @Secured("ROLE_USER")
    public Map<String,String> createArticle(
            //formatter:off
            @PathVariable("id") final long id,
            @RequestHeader(value="Authorization",required=false) String jwt,
            @RequestBody @NotBlank @Size(max=500) ArticleDto articleDto
            //formatter:on
    ) throws EntityNotFoundException {
        String username=jwtService.extractUsername(jwt.substring(7));
        Article article=Article.builder()
                .content(articleDto.getContent())
                .title(articleDto.getTitle())
                .author(userService.getUserByUsername(username))
                .topic(topicService.getTopicById(id))
                .build();
        articleService.saveArticle(article);
        Map<String,String> map=new HashMap<>();
        map.put("response","Article created with success");
        return map;
    }

    @Operation(summary = "Subscribe current user to requested topic",responses={
            @ApiResponse(responseCode="200", description = "Subscription successfully created"),
            @ApiResponse(responseCode="400", description = "Topic Id doesn't exist"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @PostMapping("/{id}/subscription")
    @Secured("ROLE_USER")
    public String subscribeTopic(
            @PathVariable("id") final long id,
            @RequestHeader(value="Authorization",required=false) String jwt
    ) throws EntityNotFoundException {
        String username=jwtService.extractUsername(jwt.substring(7));
        UserInfo user=userService.getUserByUsername(username);
        Set<Topic> userSubscriptions=user.getSubscriptions();
        if (userSubscriptions.add(topicService.getTopicById(id))) {
            user.setSubscriptions(userSubscriptions);
            userService.modifyUser(user,false);
            return "Topic subscribed with success";
        } else {
            return "Topic already subscribed";
        }
    }

    @Operation(summary = "Unsubscribe current user to requested topic",responses={
            @ApiResponse(responseCode="200", description = "Subscription successfully removed"),
            @ApiResponse(responseCode="400", description = "Topic Id doesn't exist"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @DeleteMapping("/{id}/subscription")
    @Secured("ROLE_USER")
    public String unsubscribeTopic(
            @PathVariable("id") final long id,
            @RequestHeader(value="Authorization",required=false) String jwt
    ) throws EntityNotFoundException {
        String username=jwtService.extractUsername(jwt.substring(7));
        UserInfo user=userService.getUserByUsername(username);
        Set<Topic> userSubscriptions=user.getSubscriptions();
        if (userSubscriptions.remove(topicService.getTopicById(id))) {
            user.setSubscriptions(userSubscriptions);
            userService.modifyUser(user,false);
            return "Topic unsubscribed with success";
        } else {
            return "Topic wasn't subscribed";
        }
    }
}
