package com.todolist.summary_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
