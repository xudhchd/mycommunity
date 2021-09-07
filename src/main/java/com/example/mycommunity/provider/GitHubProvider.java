package com.example.mycommunity.provider;

import com.alibaba.fastjson.JSON;
import com.example.mycommunity.dto.GitHubAccessTokenDTO;
import com.example.mycommunity.dto.GitHubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    @Value("${github.access.token.url}")
    private String accessTokenUrl;
    @Value("${github.user.url}")
    private String userUrl;

    public String getAccessToken(GitHubAccessTokenDTO gitHubAccessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(gitHubAccessTokenDTO));
        Request request = new Request.Builder()
                .url(accessTokenUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String accessToken = response.body().string();
            String token = accessToken.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(userUrl)
                .header("Authorization","token "+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String user = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(user, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
