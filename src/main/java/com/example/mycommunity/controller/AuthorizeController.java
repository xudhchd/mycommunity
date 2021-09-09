package com.example.mycommunity.controller;

import com.example.mycommunity.dto.GitHubAccessTokenDTO;
import com.example.mycommunity.dto.GitHubUser;
import com.example.mycommunity.mapper.UserMapper;
import com.example.mycommunity.model.User;
import com.example.mycommunity.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) {
        GitHubAccessTokenDTO gitHubAccessTokenDTO = new GitHubAccessTokenDTO();
        gitHubAccessTokenDTO.setClient_id(clientId);
        gitHubAccessTokenDTO.setClient_secret(clientSecret);
        gitHubAccessTokenDTO.setCode(code);
        gitHubAccessTokenDTO.setRedirect_uri(redirectUrl);
        gitHubAccessTokenDTO.setState(state);

        String accessToken = gitHubProvider.getAccessToken(gitHubAccessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if (gitHubUser!=null && gitHubUser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreat());
            user.setAvatarUrl(gitHubUser.getAvatar_url());
            httpServletResponse.addCookie(new Cookie("token",token));
            userMapper.insert(user);
            httpServletRequest.getSession().setAttribute("user",gitHubUser);
            return "redirect:/";
        }

        return "redirect:/";
    }
}
