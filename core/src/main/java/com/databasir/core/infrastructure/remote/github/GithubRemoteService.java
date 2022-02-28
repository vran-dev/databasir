package com.databasir.core.infrastructure.remote.github;

import com.databasir.common.SystemException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubRemoteService {

    private static final String TOKEN_PREFIX = "token ";

    private final GithubApiClient githubApiClient;

    private final GithubOauthClient githubOauthClient;

    public JsonNode getToken(String baseUrl,
                             String clientId,
                             String clientSecret,
                             String code) {
        TokenRequest request = TokenRequest.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .code(code)
                .build();
        String path = "/login/oauth/access_token";
        String url = baseUrl + path;
        return execute(githubOauthClient.getAccessToken(url, request.toMap()));
    }

    public JsonNode getEmail(String baseUrl, String token) {
        String path;
        if (baseUrl.contains("api.github.com")) {
            path = "/user/emails";
        } else {
            path = "/api/v3/user/emails";
        }
        String url = baseUrl + path;
        return execute(githubApiClient.getEmail(url, TOKEN_PREFIX + token));
    }

    public JsonNode getProfile(String baseUrl, String token) {
        String path;
        if (baseUrl.contains("api.github.com")) {
            path = "/user";
        } else {
            path = "/api/v3/user";
        }
        String url = baseUrl + path;
        return execute(githubApiClient.getProfile(url, TOKEN_PREFIX + token));
    }

    private <T> T execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (!response.isSuccessful()) {
                log.error("request error: " + call.request());
                throw new SystemException("Call Remote Error");
            } else {
                T body = response.body();
                return body;
            }
        } catch (IOException e) {
            throw new SystemException("System Error", e);
        }
    }
}
