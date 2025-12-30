package com.birich.task_tracker.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.birich.task_tracker.Dto.TaskResponse;
import com.birich.task_tracker.Entity.Task;
import com.birich.task_tracker.Service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public Task create(
        @PathVariable long projectId,
        @RequestBody Task task
    ){
        return taskService.create(projectId, task);
    }

    @GetMapping
    public List<TaskResponse> getAll(@PathVariable Long projectId){
        return taskService.getByProject(projectId);
    }
}
