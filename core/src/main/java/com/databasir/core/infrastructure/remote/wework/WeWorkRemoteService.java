package com.databasir.core.infrastructure.remote.wework;

import com.databasir.common.SystemException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeWorkRemoteService {

    private final WeWorkApiClient weWorkApiClient;

    public String getToken(String url,
                           String corpId,
                           String secret) {
        Map<String, String> map = new HashMap<>();
        map.put("corpid", corpId);
        map.put("corpsecret", secret);
        JsonNode data = execute(weWorkApiClient.getAccessToken(url, map));
        return data.get("access_token").asText();
    }

    public String getUserId(String url,
                            String accessToken,
                            String code) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", accessToken);
        map.put("code", code);
        return execute(weWorkApiClient.getUserInfo(url, map)).get("userid").asText();
    }

    public JsonNode getUserInfo(String url,
                                String accessToken,
                                String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("access_token", accessToken);
        map.put("userid", userId);
        return execute(weWorkApiClient.getUserInfo(url, map));
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
