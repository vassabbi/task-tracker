package com.birich.task_tracker.Controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.birich.task_tracker.Dto.CreateTaskRequest;
import com.birich.task_tracker.Service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskWebController {
    private final TaskService taskService;

    @PostMapping
    public String addTask(
        @PathVariable Long projectId,
        @RequestParam String title,
        @RequestParam(required=false) String description
    ){
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle(title);
        request.setDescription(description);

        taskService.create(projectId, request);
        return "redirect:/projects/" + projectId;
    }
}
