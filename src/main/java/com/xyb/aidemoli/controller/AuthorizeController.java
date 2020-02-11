package com.xyb.aidemoli.controller;

import com.xyb.aidemoli.dto.AccessTokenDTO;
import com.xyb.aidemoli.dto.GithubUser;
import com.xyb.aidemoli.mapper.UserMapper;
import com.xyb.aidemoli.model.User;
import com.xyb.aidemoli.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUri;


    @RequestMapping("/callback")
    public String callback(@RequestParam(name="state")String state,
                           @RequestParam(name="code")String code,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = AccessTokenDTO.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .code(code)
                .redirect_url(redirectUri)
                .state(state)
                .build();
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            User user = User.builder().accountId(String.valueOf(githubUser.getId()))
                    .name(githubUser.getName())
                    .gmtCreate(System.currentTimeMillis())
                    .gmtModified(System.currentTimeMillis())
                    .token(UUID.randomUUID().toString())
                    .avatarUrl(githubUser.getAvatar_url())
                    .build();
            response.addCookie(new Cookie("token",user.getToken()));
            userMapper.insert(user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }

}
