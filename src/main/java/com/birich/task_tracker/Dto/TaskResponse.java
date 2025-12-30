package com.birich.task_tracker.Dto;

import com.birich.task_tracker.Entity.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class TaskResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
}