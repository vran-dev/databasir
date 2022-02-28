package com.databasir.core.infrastructure.remote.github;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface GithubApiClient {

    @GET
    @Headers(value = {
            "Accept: application/json"
    })
    Call<JsonNode> getEmail(@Url String url, @Header("Authorization") String token);

    @GET
    @Headers(value = {
            "Accept: application/json"
    })
    Call<JsonNode> getProfile(@Url String url, @Header("Authorization") String token);
}
