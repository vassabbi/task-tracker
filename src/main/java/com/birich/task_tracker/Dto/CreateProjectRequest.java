package com.birich.task_tracker.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProjectRequest {

    @NotBlank(message="Name must not be empty")
    private String name;

    @Size(max = 500, message = "Description max 500 chars")
    private String description;
}
