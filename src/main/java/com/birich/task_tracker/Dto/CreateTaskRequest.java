package com.birich.task_tracker.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTaskRequest {
    @NotBlank(message="Title must not be empty")
    @Size(min = 3, max = 200, message="Task name must be 3-200 characters")
    private String title;

    @Size(max = 500, message = "Description max 500 chars")
    private String description;
}
