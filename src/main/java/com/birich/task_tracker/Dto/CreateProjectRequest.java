package com.birich.task_tracker.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateProjectRequest {

    @NotBlank(message="Name must not be empty")
    @Size(min = 3, max = 100, message="Project name must be 3-100 characters")
    private String name;

    @Size(max = 500, message = "Description max 500 chars")
    private String description;
}
