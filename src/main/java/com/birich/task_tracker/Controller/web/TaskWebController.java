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
import com.birich.task_tracker.Dto.task.CreateTaskRequest;
import com.birich.task_tracker.Dto.task.TaskView;
import com.birich.task_tracker.Dto.task.UpdateTaskRequest;
import com.birich.task_tracker.Entity.TaskStatus;
import com.birich.task_tracker.Service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskWebController {
    private final TaskService taskService;
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
            return "projects/new-project-details";
        }
        taskService.create(projectId, request);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/{taskId}/status")
    public String updateStatus(
        @PathVariable Long projectId,
        @PathVariable Long taskId,
        @RequestParam TaskStatus status
    ) {
        taskService.updateStatus(projectId, taskId, status);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/{taskId}/delete")
    public String deleteTask(
        @PathVariable Long projectId,
        @PathVariable Long taskId
    ){
        taskService.deleteTask(projectId, taskId);
        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/{taskId}/edit")
    public String editTaskForm(
        @PathVariable Long projectId,
        @PathVariable Long taskId,
        Model model
    ){
        UpdateTaskRequest form = taskService.getUpdateForm(projectId, taskId);

        model.addAttribute("taskForm", form);
        taskPageEditService.fillTaskEditPage(projectId, taskId, model);

        return "tasks/task-edit";
    }

    @PostMapping("/{taskId}")
    public String updateTask(
        @PathVariable Long projectId,
        @PathVariable Long taskId,
        @Valid @ModelAttribute("taskForm") UpdateTaskRequest request,
        BindingResult bindingResult,
        Model model
    ){
        if (bindingResult.hasErrors()){
            taskPageEditService.fillTaskEditPage(projectId, taskId, model);
            return "tasks/task-edit";
        }
        taskService.updateTask(projectId, taskId, request);
        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/{taskId}")
    public String taskDetails(
        @PathVariable Long projectId,
        @PathVariable Long taskId,
        Model model
    ){
        TaskView task = taskService.getTask(projectId, taskId);
        model.addAttribute("task", task);
        model.addAttribute("projectId", projectId);
        return "tasks/task-details";
    }
}
