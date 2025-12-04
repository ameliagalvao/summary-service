package com.todolist.summary_service.domain;

public record TaskSummaryView(Long id, String title, String description, boolean completed) {}
