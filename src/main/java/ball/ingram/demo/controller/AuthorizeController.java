package ball.ingram.demo.controller;

import ball.ingram.demo.dto.AccessTokenDTO;
import ball.ingram.demo.dto.GithubUser;
import ball.ingram.demo.provider.GithubProvider;
import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(clientId,redirectUri,
                clientSecret,code,state);


        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUer(accessToken);
        System.out.println(githubUser.getId());
        return "index";
    }


}
