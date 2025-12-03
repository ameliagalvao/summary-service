package com.todolist.summary_service.service;

import com.todolist.summary_service.adapter.TaskClient;
import com.todolist.summary_service.domain.Summary;
import com.todolist.summary_service.domain.TaskSnapshot;
import com.todolist.summary_service.domain.TaskSummaryView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final TaskClient taskClient;

    public Mono<Summary> generateSummary() {
        return taskClient.getAllTasks()
                .map(dto -> new TaskSnapshot(dto.id(), dto.completed()))
                .collectList()
                .map(tasks -> {
                    long total = tasks.size();
                    long completed = tasks.stream().filter(TaskSnapshot::completed).count();
                    double percent = total == 0 ? 0 : (completed * 100.0) / total;
                    return new Summary(total, completed, percent);
                });
    }

    public Flux<TaskSummaryView> getTasksByCompletionStatus(boolean completed) {
        return taskClient.getAllTasks()
                .filter(task -> task.completed() == completed)
                .map(task -> new TaskSummaryView(task.id(), task.title(), task.description(), task.completed()));
    }

    public Mono<Long> getTotalTasks() {
        return taskClient.getAllTasks().count();
    }
}
