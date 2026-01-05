package com.birich.task_tracker.Dto;

import com.birich.task_tracker.Entity.TaskStatus;

import lombok.Data;

@Data
public class UpdateTaskStatusRequest {
    private TaskStatus status;
}
