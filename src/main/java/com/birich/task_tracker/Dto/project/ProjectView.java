package com.birich.task_tracker.Dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ProjectView {
    private Long id;
    private String name;
    private String description;
}