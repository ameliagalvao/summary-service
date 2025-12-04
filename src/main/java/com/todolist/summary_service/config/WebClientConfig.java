package com.todolist.summary_service.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${task.service.url}")
    private String taskServiceUrl;

    @Bean
    public WebClient taskWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(taskServiceUrl)
                .build();
    }
}


