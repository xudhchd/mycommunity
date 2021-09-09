package com.example.mycommunity.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class GitHubAccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
