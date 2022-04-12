package com.databasir.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

    @Bean
    public Executor mailThreadPoolTaskExecutor() {
        final int maxCorePoolSize = 16;
        final int maxPoolSize = 32;
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int availableProcessorCount = Runtime.getRuntime().availableProcessors();
        int corePoolSize = Math.min(maxCorePoolSize, availableProcessorCount);
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
