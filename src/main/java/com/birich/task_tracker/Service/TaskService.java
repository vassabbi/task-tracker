package com.birich.task_tracker.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.birich.task_tracker.Dto.CreateTaskRequest;
import com.birich.task_tracker.Dto.TaskResponse;
import com.birich.task_tracker.Dto.UpdateTaskRequest;
import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.Task;
import com.birich.task_tracker.Entity.TaskStatus;
import com.birich.task_tracker.Repository.TaskRepository;
import com.birich.task_tracker.Repository.TaskSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskResponse create(Project project, CreateTaskRequest request){
        if (request.getDueDate() != null &&
            request.getDueDate().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Due date cannot be in the past");
        }

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setProject(project);
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());

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

    public void updateStatus(Project project, Long taskId, TaskStatus taskStatus){
        Task task = taskRepository.findByIdAndProject(taskId, project)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        task.setStatus(taskStatus);
        taskRepository.save(task);
    }

    public void deleteTask(Project project, Long taskId){
        Task task = taskRepository.findByIdAndProject(taskId, project)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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

    public void updateTask(Project project, Long taskId, UpdateTaskRequest request){
        Task task = taskRepository.findByIdAndProject(taskId, project)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (request.getTitle() != null){
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null){
            task.setDescription(request.getDescription());
        }

        if (request.getPriority() != null){
            task.setPriority(request.getPriority());
        }

        if (request.getDueDate() != null){
            task.setDueDate(request.getDueDate());
        }

        if (request.getStatus() != null){
            updateStatus(task, request.getStatus());
        }
        taskRepository.save(task);
    }

    private void updateStatus(Task task, TaskStatus status){
        if (status == TaskStatus.DONE && task.getCompletedAt() == null){
            task.setCompletedAt(LocalDateTime.now());
        }

        task.setStatus(status);
    }

    public Task getTask(Project project, Long taskId){
        Task task = taskRepository.findByIdAndProject(taskId, project)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return task;
    }

    public UpdateTaskRequest getUpdateForm(Project project, Long taskId){
        Task task = taskRepository.findByIdAndProject(taskId, project)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        UpdateTaskRequest form = new UpdateTaskRequest();
        form.setTitle(task.getTitle());
        form.setDescription(task.getDescription());
        form.setPriority(task.getPriority());
        form.setDueDate(task.getDueDate());
        form.setStatus(task.getStatus());

        return form;
    }
}
