package com.databasir.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

    @Bean
    public Executor mailThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int availableProcessorCount = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(availableProcessorCount << 1);
        executor.setMaxPoolSize(32);
        executor.setAllowCoreThreadTimeOut(true);
        return executor;
    }
}
