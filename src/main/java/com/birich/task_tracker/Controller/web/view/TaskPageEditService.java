package com.birich.task_tracker.Controller.web.view;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.birich.task_tracker.Dto.project.ProjectView;
import com.birich.task_tracker.Entity.TaskPriority;
import com.birich.task_tracker.Entity.TaskStatus;
import com.birich.task_tracker.Service.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskPageEditService {

    private final ProjectService projectService;

    public void fillTaskEditPage(
        Long projectId,
        Long taskId,
        Model model
    ){
        ProjectView projectView = projectService.getProjectView(projectId);
        model.addAttribute("project", projectView);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("taskId", taskId);
        
    }
}
