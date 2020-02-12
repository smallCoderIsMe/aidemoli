package com.xyb.aidemoli.controller;

import com.xyb.aidemoli.dto.AccessTokenDTO;
import com.xyb.aidemoli.dto.GithubUser;
import com.xyb.aidemoli.mapper.UserMapper;
import com.xyb.aidemoli.model.User;
import com.xyb.aidemoli.provider.GithubProvider;
import com.xyb.aidemoli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

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
        if(accessToken == null){
            return "redirect:/";
        }
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            User user = User.builder().accountId(String.valueOf(githubUser.getId()))
                    .name(githubUser.getName())
                    .token(UUID.randomUUID().toString())
                    .avatarUrl(githubUser.getAvatar_url())
                    .build();
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
