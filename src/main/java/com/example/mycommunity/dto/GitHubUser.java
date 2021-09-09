package com.example.mycommunity.dto;

import lombok.Data;

@Data
public class GitHubUser {
    private Long id;
    private String login;
    private String bio;
    private String name;
    private String avatar_url;
}
