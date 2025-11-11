package com.todolist.summary_service.service;

import com.todolist.summary_service.client.TaskClient;
import com.todolist.summary_service.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryService {
    private final TaskClient taskClient;

    public SummaryService(TaskClient taskClient) {
        this.taskClient = taskClient;
    }

    public long countTasks() {
        return taskClient.getAllTasks().size();
    }

    public List<TaskDTO> getOpenTasks() {
        return taskClient.getAllTasks().stream()
                .filter(task -> !task.isCompleted())
                .toList();
    }

    public List<TaskDTO> getClosedTasks() {
        return taskClient.getAllTasks().stream()
                .filter(TaskDTO::isCompleted)
                .toList();
    }
}
