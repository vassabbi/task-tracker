package com.birich.task_tracker.Controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.birich.task_tracker.Controller.web.view.ProjectPageService;
import com.birich.task_tracker.Controller.web.view.TaskPageEditService;
import com.birich.task_tracker.Dto.CreateTaskRequest;
import com.birich.task_tracker.Dto.UpdateTaskRequest;
import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.Task;
import com.birich.task_tracker.Entity.TaskStatus;
import com.birich.task_tracker.Service.ProjectService;
import com.birich.task_tracker.Service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskWebController {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final ProjectPageService projectPageService;
    private final TaskPageEditService taskPageEditService;

    @PostMapping
    public String addTask(
        @PathVariable Long projectId,
        @Valid @ModelAttribute("taskForm") CreateTaskRequest request,
        BindingResult bindingResult,
        Model model
    ){
        if (bindingResult.hasErrors()){
            projectPageService.fillProjectDetailsPage(projectId, 0, null, null, model);
            return "project-details";
        }
        Project project = projectService.getProjectForCurrentUser(projectId);
        taskService.create(project, request);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/{taskId}/status")
    public String updateStatus(
        @PathVariable Long projectId,
        @PathVariable Long taskId,
        @RequestParam TaskStatus status
    ) {
        Project project = projectService.getProjectForCurrentUser(projectId);
        taskService.updateStatus(project, taskId, status);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/{taskId}/delete")
    public String deleteTask(
        @PathVariable Long projectId,
        @PathVariable Long taskId
    ){
        Project project = projectService.getProjectForCurrentUser(projectId);
        taskService.deleteTask(project, taskId);
        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/{taskId}/edit")
    public String editTaskForm(
        @PathVariable Long projectId,
        @PathVariable Long taskId,
        Model model
    ){
        Project project = projectService.getProjectForCurrentUser(projectId);
        UpdateTaskRequest form = taskService.getUpdateForm(project, taskId);

        model.addAttribute("taskForm", form);
        taskPageEditService.fillTaskEditPage(project, taskId, model);

        return "task-edit";
    }

    @PostMapping("/{taskId}")
    public String updateTask(
        @PathVariable Long projectId,
        @PathVariable Long taskId,
        @Valid @ModelAttribute("taskForm") UpdateTaskRequest request,
        BindingResult bindingResult,
        Model model
    ){
        Project project = projectService.getProjectForCurrentUser(projectId);
        if (bindingResult.hasErrors()){
            taskPageEditService.fillTaskEditPage(project, taskId, model);
            return "task-edit";
        }
        taskService.updateTask(project, taskId, request);
        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/{taskId}")
    public String taskDetails(
        @PathVariable Long projectId,
        @PathVariable Long taskId,
        Model model
    ){
        Project project = projectService.getProjectForCurrentUser(projectId);
        Task task = taskService.getTask(project, taskId);
        model.addAttribute("task", task);
        return "task-details";
    }
}
