package com.todolist.summary_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient taskWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8080")
                .build();
    }
}


