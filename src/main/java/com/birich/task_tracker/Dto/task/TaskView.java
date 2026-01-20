package com.birich.task_tracker.Dto.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.birich.task_tracker.Entity.TaskPriority;
import com.birich.task_tracker.Entity.TaskStatus;

import lombok.Data;

@Data
public class TaskView {
    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final LocalDate dueDate;
    private final LocalDateTime createdAt;
}