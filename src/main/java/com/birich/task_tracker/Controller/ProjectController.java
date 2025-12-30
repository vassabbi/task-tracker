package com.birich.task_tracker.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.birich.task_tracker.Dto.ProjectResponse;
import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponse> getAll(){
        return projectService.findAll();
    }

    @PostMapping
    public Project create(@RequestBody Project project){
        return projectService.create(project);
    }
}
