package com.todolist.summary_service.service;

import com.todolist.summary_service.adapter.TaskClient;
import com.todolist.summary_service.adapter.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SummaryServiceTest {

    private TaskClient taskClient;
    private SummaryService summaryService;

    @BeforeEach
    void setUp() {
        taskClient = mock(TaskClient.class);
        summaryService = new SummaryService(taskClient);
    }

    @Test
    void generateSummary_shouldReturnCorrectSummary() {
        List<TaskDTO> tasks = List.of(
                new TaskDTO(1L, "Task 1", "Description 1", true),
                new TaskDTO(2L, "Task 2", "Description 2", false)
        );

        when(taskClient.getAllTasks()).thenReturn(Flux.fromIterable(tasks));

        StepVerifier.create(summaryService.generateSummary())
                .expectNextMatches(summary ->
                        summary.totalTasks() == 2 &&
                                summary.completedTasks() == 1 &&
                                summary.percentCompleted() == 50.0
                )
                .verifyComplete();

        verify(taskClient).getAllTasks();
    }

    @Test
    void getTasksByCompletionStatus_shouldReturnFilteredTasks() {
        List<TaskDTO> tasks = List.of(
                new TaskDTO(1L, "Task 1", "Desc", true),
                new TaskDTO(2L, "Task 2", "Desc", false)
        );

        when(taskClient.getAllTasks()).thenReturn(Flux.fromIterable(tasks));

        StepVerifier.create(summaryService.getTasksByCompletionStatus(true))
                .expectNextMatches(task -> task.id() == 1L)
                .verifyComplete();

        verify(taskClient).getAllTasks();
    }

    @Test
    void getTotalTasks_shouldReturnCount() {
        when(taskClient.getAllTasks()).thenReturn(Flux.just(
                new TaskDTO(1L, "Task 1", "Desc", true),
                new TaskDTO(2L, "Task 2", "Desc", false)
        ));

        StepVerifier.create(summaryService.getTotalTasks())
                .expectNext(2L)
                .verifyComplete();

        verify(taskClient).getAllTasks();
    }
}

