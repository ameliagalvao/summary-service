package com.todolist.summary_service.client;

import com.todolist.summary_service.dto.TaskDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "taskClient", url = "http://task-service:8080")
public interface TaskClient {

    @GetMapping("/tasks")
    List<TaskDTO> getAllTasks();
}
