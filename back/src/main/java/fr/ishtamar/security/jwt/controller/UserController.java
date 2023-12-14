package fr.ishtamar.security.jwt.controller;

import fr.ishtamar.security.jwt.dto.ArticleDto;
import fr.ishtamar.security.jwt.dto.AuthRequest;
import fr.ishtamar.security.jwt.dto.ModifyUserRequest;
import fr.ishtamar.security.jwt.dto.UserDto;
import fr.ishtamar.security.jwt.entity.Topic;
import fr.ishtamar.security.jwt.entity.UserInfo;
import fr.ishtamar.security.jwt.exceptionhandler.EmailAlreadyUsedException;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.security.jwt.exceptionhandler.InvalidPasswordException;
import fr.ishtamar.security.jwt.mapper.ArticleMapper;
import fr.ishtamar.security.jwt.mapper.UserMapper;
import fr.ishtamar.security.jwt.service.ArticleService;
import fr.ishtamar.security.jwt.service.JwtService;
import fr.ishtamar.security.jwt.service.UserInfoService;
import fr.ishtamar.security.jwt.service.impl.ArticleServiceImpl;
import fr.ishtamar.security.jwt.service.impl.UserInfoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
public class UserController {

    @Autowired
    private final UserInfoService service=new UserInfoServiceImpl();
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService=new ArticleServiceImpl();

    @Operation(hidden=true)
    @GetMapping("/auth/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @Operation(summary = "register new user",responses={
            @ApiResponse(responseCode="200", description = "User successfully created"),
            @ApiResponse(responseCode="400", description = "User already exists")
    })
    @PostMapping("/auth/register")
    public Map<String,String> addNewUser(@RequestBody UserInfo userInfo) {
        service.createUser(userInfo);
        Map<String,String>map=new HashMap<>();
        map.put("token",jwtService.generateToken(userInfo.getEmail()));
        return map;
    }

    @Operation(summary = "logins user and returns JWT",responses={
            @ApiResponse(responseCode="200", description = "Token successfully created"),
            @ApiResponse(responseCode="404", description = "Username not found")
    })
    @PostMapping("/auth/login")
    public Map<String,String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            Map<String,String>map=new HashMap<>();
            map.put("token",jwtService.generateToken(authRequest.getEmail()));
            return map;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @Operation(summary = "gets personal data from logged in user",responses={
            @ApiResponse(responseCode="200", description = "Personal data is displayed"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @GetMapping("/auth/me")
    @Secured("ROLE_USER")
    public UserDto userProfile(@RequestHeader(value="Authorization",required=false) String jwt) {
        return userMapper.toDto(service.getUserByUsername(jwtService.extractUsername(jwt.substring(7))));
    }

    @Operation(summary = "changes personal data from logged in user",responses={
            @ApiResponse(responseCode="200", description = "Personal data is changed, new JWT is displayed"),
            @ApiResponse(responseCode="400", description = "Email is already used or password is not valid"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @PatchMapping("/auth/me")
    @Secured("ROLE_USER")
    public Map<String,String> userModifyProfile(
            //@formatter: off
            @RequestHeader(value="Authorization",required=false) String jwt,
            @RequestBody ModifyUserRequest request
            //@formatter: on
    ) throws EntityNotFoundException, EmailAlreadyUsedException, InvalidPasswordException {
        //get data from user
        String username=jwtService.extractUsername(jwt.substring(7));
        UserInfo candidate=service.getUserByUsername(username);

        //modify only name, email and password
        if(request.getName()!=null && !request.getName().isEmpty()) candidate.setName(request.getName());
        if(request.getEmail()!=null && !request.getEmail().isEmpty()) candidate.setEmail(request.getEmail());
        if(request.getPassword()!=null && !request.getPassword().isEmpty()) candidate.setPassword(request.getPassword());

        //Try to save user (if email is OK, password is valid, name is OK)
        service.modifyUser(candidate, request.getPassword()!=null && !request.getPassword().isEmpty());

        //prepare a new JWT to show (not executed if there's an error before)
        Map<String,String>map=new HashMap<>();
        map.put("token",jwtService.generateToken(candidate.getEmail()));

        return map;
    }

    @Operation(summary = "gets personal data from user by id",responses={
            @ApiResponse(responseCode="200", description = "Personal data is displayed"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @GetMapping("/user/{id}")
    @Secured("ROLE_USER")
    public UserDto getUserById(@PathVariable("id") final long id) throws EntityNotFoundException {
        return userMapper.toDto(service.getUserById(id));
    }

    @Operation(summary = "gets all relevant articles from user by id",responses={
            @ApiResponse(responseCode="200", description = "Relevant articles are displayed"),
            @ApiResponse(responseCode="403", description = "Access unauthorized")
    })
    @GetMapping("/user/{id}/articles")
    @Secured("ROLE_USER")
    public List<ArticleDto> getArticlesForUserById(@PathVariable("id") final long id) throws EntityNotFoundException {
        List<Long> ids=service.getUserById(id).getSubscriptions().stream()
                .map(Topic::getId)
                .collect(Collectors.toList());
        return articleMapper.toDto(articleService.getAllArticlesInTopicIds(ids));
    }
}
