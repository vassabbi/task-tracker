package com.birich.task_tracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.birich.task_tracker.Dto.CreateTaskRequest;
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

    public TaskResponse create(Long projectId, CreateTaskRequest request){
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));
        
        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setProject(project);
        task.setStatus(TaskStatus.TODO);

        Task saved = taskRepository.save(task);

        return new TaskResponse(
            saved.getId(), 
            saved.getTitle(),
            saved.getDescription(), 
            saved.getStatus()
        );
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
