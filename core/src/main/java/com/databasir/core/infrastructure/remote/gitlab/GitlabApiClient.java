package com.databasir.core.infrastructure.remote.gitlab;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.*;

public interface GitlabApiClient {
    @GET
    @Headers(value = {
            "Accept: application/json"
    })
    Call<JsonNode> getUser(@Url String url, @Header("Authorization") String token);

    @POST
    @Headers(value = {
            "Accept: application/json"
    })
    Call<JsonNode> getAccessToken(@Url String url);
}
