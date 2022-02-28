package com.databasir.core.infrastructure.remote.github;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import java.util.Map;

public interface GithubOauthClient {

    @Headers(value = {
            "Accept: application/json"
    })
    @POST
    Call<JsonNode> getAccessToken(@Url String url, @QueryMap Map<String, String> request);

}
