package com.birich.task_tracker.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.birich.task_tracker.Dto.CreateTaskRequest;
import com.birich.task_tracker.Dto.TaskResponse;
import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.Task;
import com.birich.task_tracker.Entity.TaskStatus;
import com.birich.task_tracker.Repository.ProjectRepository;
import com.birich.task_tracker.Repository.TaskRepository;
import com.birich.task_tracker.Repository.TaskSpecifications;

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
        return taskRepository.findByProjectIdOrderByIdAsc(projectId).stream()
            .map(t -> new TaskResponse(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus()
            ))
            .toList();
    }

    public Page<Task> getByProject(
        Long projectId,
        int page,
        int size
    ){
        Pageable pageable = PageRequest.of(
            page, 
            size,
            Sort.by("id").descending()
        );
        return taskRepository.findByProjectId(projectId, pageable);
    }

    public void updateStatus(Long taskId, TaskStatus taskStatus){
        Task task = taskRepository.findById(taskId)
            .orElseThrow();
        task.setStatus(taskStatus);
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId){
        Task task = taskRepository.findById(taskId)
            .orElseThrow();
        taskRepository.delete(task);
    }

    public Page<Task> searchTasks(
        Project project,
        TaskStatus status,
        String title,
        int page,
        int size
    ){
        Pageable pageable = PageRequest.of(
            page, 
            size, 
            Sort.by("id").descending()
        );

        Specification<Task> spec = 
            Specification.where(TaskSpecifications.hasProject(project))
                        .and(TaskSpecifications.hasStatus(status))
                        .and(TaskSpecifications.titleContains(title));
        
        return taskRepository.findAll(spec, pageable);
    }
}
