package ball.ingram.demo.controller;

import ball.ingram.demo.dto.AccessTokenDTO;
import ball.ingram.demo.dto.GithubUser;
import ball.ingram.demo.mapper.UserMapper;
import ball.ingram.demo.model.User;
import ball.ingram.demo.model.UserExample;
import ball.ingram.demo.provider.GithubProvider;
import ball.ingram.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
@Slf4j
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;



    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(clientId,redirectUri,
                clientSecret,code,state);


        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUer(accessToken);
        if(githubUser != null && githubUser.getId() !=  0){
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            String token = UUID.randomUUID().toString();
            System.out.println(token);
            System.out.print(token);
            user.setToken(token);
            user.setName(githubUser.getName());

            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            UserExample example = new UserExample();
            example.createCriteria()
                    .andAccountIdEqualTo(user.getAccountId());
            List<User> users = userMapper.selectByExample(example);
            request.getSession().setAttribute("user",users.get(0));

           return "redirect:/";
        }else{
            log.error("github error ,{}",githubUser);
            return "redirect:/";
        }


    }
    @GetMapping("/logout")
    public String logOut (HttpServletRequest request,
                          HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }


}
