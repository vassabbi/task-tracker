package com.birich.task_tracker.Controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.birich.task_tracker.Service.ProjectService;
import com.birich.task_tracker.Service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProjectWebController {
    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping("/")
    public String projects(Model model){
        model.addAttribute("projects", projectService.findAll());
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model){
        model.addAttribute("tasks", taskService.getByProject(id));
        model.addAttribute("projectId", id);
        return "project-details";
    }
}
