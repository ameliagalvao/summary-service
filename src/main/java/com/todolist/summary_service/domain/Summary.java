package com.todolist.summary_service.domain;

public record Summary(long totalTasks, long completedTasks, double percentCompleted) {}
