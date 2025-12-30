package com.birich.task_tracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.birich.task_tracker.Dto.TaskResponse;
import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.Task;
import com.birich.task_tracker.Entity.TaskStatus;
import com.birich.task_tracker.Repository.ProjectRepository;
import com.birich.task_tracker.Repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public Task create(Long projectId, Task task){
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));
        
        task.setProject(project);
        task.setStatus(TaskStatus.TODO);

        return taskRepository.save(task);
    }

    public List<TaskResponse> getByProject(Long projectId){
        return taskRepository.findByProjectId(projectId).stream()
            .map(t -> new TaskResponse(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus()
            ))
            .toList();
    }
}
