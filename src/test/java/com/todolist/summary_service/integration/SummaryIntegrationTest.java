package com.todolist.summary_service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
public class SummaryIntegrationTest {

    @Container
    static MockServerContainer mockServer = new MockServerContainer(DockerImageName.parse("mockserver/mockserver").withTag("5.15.0"))
            ;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("task.service.url", () -> "http://" + mockServer.getHost() + ":" + mockServer.getServerPort());
    }

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setupMockServer() {
        new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/tasks")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("""
                                        [
                                          {"id":1,"title":"Task 1","description":"Desc 1","completed":true},
                                          {"id":2,"title":"Task 2","description":"Desc 2","completed":false}
                                        ]
                                        """)
                );
    }

    @Test
    void shouldReturnCorrectSummaryFromTaskServiceMock() {
        webTestClient.get()
                .uri("/summary")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.totalTasks").isEqualTo(2)
                .jsonPath("$.completedTasks").isEqualTo(1)
                .jsonPath("$.percentCompleted").isEqualTo(50.0);
    }

    @Test
    void shouldReturnOnlyCompletedTasks() {
        webTestClient.get()
                .uri("/summary/completed")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].completed").isEqualTo(true)
                .jsonPath("$[0].title").isEqualTo("Task 1");
    }

    @Test
    void shouldReturnOnlyPendingTasks() {
        webTestClient.get()
                .uri("/summary/pending")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].completed").isEqualTo(false)
                .jsonPath("$[0].title").isEqualTo("Task 2");
    }

    @Test
    void shouldReturnTotalCount() {
        webTestClient.get()
                .uri("/summary/total")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class)
                .isEqualTo(2L);
    }
}
