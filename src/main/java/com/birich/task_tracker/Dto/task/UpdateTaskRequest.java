package com.birich.task_tracker.Dto.task;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.birich.task_tracker.Entity.TaskPriority;
import com.birich.task_tracker.Entity.TaskStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTaskRequest {

    @NotBlank(message="Title must not be empty")
    @Size(min = 3, max = 200, message="Task name must be 3-200 characters")
    private String title;

    @Size(max = 500, message = "Description max 500 chars")
    private String description;

    @NotNull
    private TaskPriority priority;

    @NotNull
    private TaskStatus status;

    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;
}
