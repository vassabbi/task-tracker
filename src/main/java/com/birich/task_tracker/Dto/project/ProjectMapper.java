package com.birich.task_tracker.Dto.project;

import org.springframework.stereotype.Component;

import com.birich.task_tracker.Entity.Project;

@Component
public class ProjectMapper {
    public ProjectView toView(Project project){
        return new ProjectView(
            project.getId(),
            project.getName(),
            project.getDescription()
        );
    }
}
