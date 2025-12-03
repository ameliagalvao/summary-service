package com.todolist.summary_service.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class TaskClient {

    private final WebClient taskWebClient;

    public Flux<TaskDTO> getAllTasks() {
        return taskWebClient.get()
                .uri("/tasks")
                .retrieve()
                .bodyToFlux(TaskDTO.class);
    }
}
