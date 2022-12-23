package com.databasir.core.infrastructure.remote;

import com.databasir.core.infrastructure.remote.github.GithubApiClient;
import com.databasir.core.infrastructure.remote.github.GithubOauthClient;
import com.databasir.core.infrastructure.remote.gitlab.GitlabApiClient;
import com.databasir.core.infrastructure.remote.wework.WeWorkApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ClientConfig {

    @Bean
    public GithubApiClient githubApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(GithubApiClient.class);
    }

    @Bean
    public GithubOauthClient githubOauthClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://github.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(GithubOauthClient.class);
    }

    @Bean
    public GitlabApiClient gitlabApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gitlab.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(GitlabApiClient.class);
    }

    @Bean
    public WeWorkApiClient weWorkApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://qyapi.weixin.qq.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(WeWorkApiClient.class);
    }
}
