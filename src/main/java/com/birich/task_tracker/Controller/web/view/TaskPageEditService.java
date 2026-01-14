package com.birich.task_tracker.Controller.web.view;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.TaskPriority;
import com.birich.task_tracker.Entity.TaskStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskPageEditService {

    public void fillTaskEditPage(
        Project project,
        Long taskId,
        Model model
    ){
        model.addAttribute("project", project);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("taskId", taskId);
        
    }
}
