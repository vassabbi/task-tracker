package com.birich.task_tracker.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
}