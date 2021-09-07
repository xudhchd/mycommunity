package com.example.mycommunity.controller;

import com.example.mycommunity.dto.GitHubAccessTokenDTO;
import com.example.mycommunity.dto.GitHubUser;
import com.example.mycommunity.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state) {
        GitHubAccessTokenDTO gitHubAccessTokenDTO = new GitHubAccessTokenDTO();
        gitHubAccessTokenDTO.setClient_id(clientId);
        gitHubAccessTokenDTO.setClient_secret(clientSecret);
        gitHubAccessTokenDTO.setCode(code);
        gitHubAccessTokenDTO.setRedirect_uri(redirectUrl);
        gitHubAccessTokenDTO.setState(state);

        String token = gitHubProvider.getAccessToken(gitHubAccessTokenDTO);
        GitHubUser user = gitHubProvider.getUser(token);
        System.out.println(user.getLogin());
        System.out.println(user.getId());

        return "index";
    }
}
