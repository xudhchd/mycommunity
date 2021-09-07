package com.example.mycommunity.controller;

import com.example.mycommunity.dto.GitHubAccessTokenDTO;
import com.example.mycommunity.dto.GitHubUser;
import com.example.mycommunity.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state) {
        GitHubAccessTokenDTO gitHubAccessTokenDTO = new GitHubAccessTokenDTO();
        gitHubAccessTokenDTO.setClient_id("c6396827f8c8d6bc263a");
        gitHubAccessTokenDTO.setClient_secret("fc589fc14b8b52998fa959c3df25e808a4cc0278");
        gitHubAccessTokenDTO.setCode(code);
        gitHubAccessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        gitHubAccessTokenDTO.setState(state);

        String token = gitHubProvider.getAccessToken(gitHubAccessTokenDTO);
        GitHubUser user = gitHubProvider.getUser(token);
        System.out.println(user.getLogin());
        System.out.println(user.getId());

        return "index";
    }
}
