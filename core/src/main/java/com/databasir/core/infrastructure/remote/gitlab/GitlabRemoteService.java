package com.databasir.core.infrastructure.remote.gitlab;

import com.databasir.common.SystemException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitlabRemoteService {

    private final GitlabApiClient gitlabApiClient;

    public JsonNode getAccessToken(String baseUrl,
                                   String code,
                                   String clientId,
                                   String clientSecret,
                                   String redirectUri) {
        String path = "/oauth/token";
        String uri = baseUrl + path;
        String uriStr = UriComponentsBuilder.fromUriString(uri)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", redirectUri)
                .encode()
                .toUriString();
        return execute(gitlabApiClient.getAccessToken(uriStr));
    }

    public JsonNode getUser(String baseUrl, String accessToken) {
        String tokenHeaderValue = "Bearer " + accessToken;
        String uri = baseUrl + "/api/v4/user";
        return execute(gitlabApiClient.getUser(uri, tokenHeaderValue));
    }

    private <T> T execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (!response.isSuccessful()) {
                log.error("request error: " + call.request() + ", response = " + response);
                throw new SystemException("Call Remote Error");
            } else {
                log.info("response " + response);
                T body = response.body();
                return body;
            }
        } catch (IOException e) {
            throw new SystemException("System Error", e);
        }
    }
}
