package com.birich.task_tracker.Controller.web;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.birich.task_tracker.Dto.CreateProjectRequest;
import com.birich.task_tracker.Entity.Task;
import com.birich.task_tracker.Entity.TaskStatus;
import com.birich.task_tracker.Service.ProjectService;
import com.birich.task_tracker.Service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProjectWebController {
    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping("/")
    public String projects(Model model){
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("projectForm", new CreateProjectRequest());
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String projectDetails(
        @PathVariable Long id, 
        @RequestParam(defaultValue="0") int page,
        @RequestParam(required = false) TaskStatus status,
        @RequestParam(required = false) String title,
        Model model
    ){
        int pageSize = 5;
        Page<Task> taskPage = taskService.searchTasks(
            id, 
            status,
            title,
            page, 
            pageSize
        );
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("taskPage", taskPage);
        model.addAttribute("projectId", id);
        model.addAttribute("status", status);
        model.addAttribute("title", title);
        model.addAttribute("statuses", TaskStatus.values());
        return "project-details";
    }

    @PostMapping("/projects")
    public String createProject(
        @Valid @ModelAttribute("projectForm") CreateProjectRequest request,
        BindingResult bindingResult,
        Model model
    ){
        if (bindingResult.hasErrors()){
            model.addAttribute("projects", projectService.findAll());
            return "projects";
        }
        projectService.create(request);
        return "redirect:/";
    }
}
