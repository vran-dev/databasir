package com.databasir.core;

import com.databasir.core.config.AsyncConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude = {
        R2dbcAutoConfiguration.class
})
@ComponentScan(
        basePackages = "com.databasir",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AsyncConfig.class)
        })
public class CoreTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreTestApplication.class, args);
    }
}
