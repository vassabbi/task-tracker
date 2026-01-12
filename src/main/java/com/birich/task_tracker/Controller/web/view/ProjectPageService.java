package com.birich.task_tracker.Controller.web.view;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.Task;
import com.birich.task_tracker.Entity.TaskStatus;
import com.birich.task_tracker.Service.ProjectService;
import com.birich.task_tracker.Service.TaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectPageService {
    private final ProjectService projectService;
    private final TaskService taskService;
    
    public void fillProjectDetailsPage(
        Long projectId,
        Integer page,
        TaskStatus status,
        String title,
        Model model
    ){
        Project project = projectService.getProjectForCurrentUser(projectId);
        int pageSize = 5;
        Page<Task> taskPage = taskService.searchTasks(
            project, 
            status,
            title,
            page, 
            pageSize
        );
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("taskPage", taskPage);
        model.addAttribute("project", project);
        model.addAttribute("status", status);
        model.addAttribute("title", title);
        model.addAttribute("statuses", TaskStatus.values());
    }
}
