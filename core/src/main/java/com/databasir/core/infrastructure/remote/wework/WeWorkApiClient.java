package com.databasir.core.infrastructure.remote.wework;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import java.util.Map;

public interface WeWorkApiClient {

    @GET
    @Headers(value = {
            "Accept: application/json"
    })
    Call<JsonNode> getUserInfo(@Url String url, @QueryMap Map<String, String> request);

    @GET
    @Headers(value = {
            "Accept: application/json"
    })
    Call<JsonNode> getAccessToken(@Url String url, @QueryMap Map<String, String> request);
}
