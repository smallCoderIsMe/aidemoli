package com.xyb.aidemoli.controller;

import com.xyb.aidemoli.dto.AccessTokenDTO;
import com.xyb.aidemoli.dto.GithubUser;
import com.xyb.aidemoli.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUri;


    @RequestMapping("/callback")
    public String callback(@RequestParam(name="state")String state,
                           @RequestParam(name="code")String code) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();//shift+enter快速换行
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getId()+githubUser.getLogin());
        return "index";
    }

}
