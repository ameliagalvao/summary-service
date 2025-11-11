package com.todolist.summary_service.controller;

import com.todolist.summary_service.dto.TaskDTO;
import com.todolist.summary_service.service.SummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SummaryController {
    private final SummaryService service;

    public SummaryController(SummaryService service) {
        this.service = service;
    }

    @GetMapping("/summary/count")
    public long countTasks() {
        return service.countTasks();
    }

    @GetMapping("/summary/open")
    public List<TaskDTO> getOpenTasks() {
        return service.getOpenTasks();
    }

    @GetMapping("/summary/closed")
    public List<TaskDTO> getClosedTasks() {
        return service.getClosedTasks();
    }
}
