package com.databasir.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ProjectSyncTaskThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor projectSyncTaskThreadPoolTaskExecutor() {
        final int maxCorePoolSize = 12;
        final int maxPoolSize = 32;

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int availableProcessorCount = Runtime.getRuntime().availableProcessors() + 2;
        int corePoolSize = Math.min(maxCorePoolSize, availableProcessorCount);
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        return executor;
    }

}
