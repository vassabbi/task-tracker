package com.birich.task_tracker.Controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.birich.task_tracker.Dto.project.CreateProjectRequest;
import com.birich.task_tracker.Dto.project.ProjectView;
import com.birich.task_tracker.Service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public List<ProjectView> getAll(){
        return projectService.findAllForCurrentUser();
    }

    @PostMapping
    public ProjectView create(@Valid @RequestBody CreateProjectRequest project){
        return projectService.create(project);
    }
}
