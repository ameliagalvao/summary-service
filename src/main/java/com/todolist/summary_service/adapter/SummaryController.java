package com.todolist.summary_service.adapter;

import com.todolist.summary_service.domain.TaskSummaryView;
import com.todolist.summary_service.service.SummaryService;
import com.todolist.summary_service.domain.Summary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping
    public Mono<Summary> getSummary() {
        return summaryService.generateSummary();
    }

    @GetMapping("/completed")
    public Flux<TaskSummaryView> getCompletedTasks() {
        return summaryService.getTasksByCompletionStatus(true);
    }

    @GetMapping("/pending")
    public Flux<TaskSummaryView> getPendingTasks() {
        return summaryService.getTasksByCompletionStatus(false);
    }

    @GetMapping("/total")
    public Mono<Long> getTotalTasks() {
        return summaryService.getTotalTasks();
    }
}

