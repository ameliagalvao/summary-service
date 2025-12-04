package com.todolist.summary_service.adapter;

import com.todolist.summary_service.domain.Summary;
import com.todolist.summary_service.domain.TaskSummaryView;
import com.todolist.summary_service.service.SummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = SummaryController.class)
class SummaryControllerTest {

    @MockitoBean
    private SummaryService summaryService;

    @Autowired
    private WebTestClient webTestClient;


    @Test
    void getSummary_shouldReturnSummary() {
        Summary summary = new Summary(10L, 5L, 50.0);
        when(summaryService.generateSummary()).thenReturn(Mono.just(summary));

        webTestClient.get().uri("/summary")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Summary.class)
                .isEqualTo(summary);

        verify(summaryService).generateSummary();
    }

    @Test
    void getCompletedTasks_shouldReturnCompletedTasks() {
        TaskSummaryView task = new TaskSummaryView(1L, "Task 1", "Desc", true);
        when(summaryService.getTasksByCompletionStatus(true)).thenReturn(Flux.just(task));

        webTestClient.get().uri("/summary/completed")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskSummaryView.class)
                .hasSize(1);

        verify(summaryService).getTasksByCompletionStatus(true);
    }

    @Test
    void getTotalTasks_shouldReturnTaskCount() {
        when(summaryService.getTotalTasks()).thenReturn(Mono.just(42L));

        webTestClient.get().uri("/summary/total")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class)
                .isEqualTo(42L);

        verify(summaryService).getTotalTasks();
    }
}
