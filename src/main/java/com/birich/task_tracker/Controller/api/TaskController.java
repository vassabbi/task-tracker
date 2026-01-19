package com.birich.task_tracker.Controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.birich.task_tracker.Dto.task.CreateTaskRequest;
import com.birich.task_tracker.Dto.task.TaskView;
import com.birich.task_tracker.Service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskView create(
        @PathVariable long projectId,
        @Valid @RequestBody CreateTaskRequest task
    ){
        return taskService.create(projectId, task);
    }

    @GetMapping
    public List<TaskView> getAll(@PathVariable Long projectId){
        return taskService.getByProject(projectId);
    }
}
