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

import com.birich.task_tracker.Dto.task.CreateTaskRequest;
import com.birich.task_tracker.Dto.task.TaskMapper;
import com.birich.task_tracker.Dto.task.TaskView;
import com.birich.task_tracker.Dto.task.UpdateTaskRequest;
import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.Task;
import com.birich.task_tracker.Entity.TaskStatus;
import com.birich.task_tracker.Repository.TaskRepository;
import com.birich.task_tracker.Repository.TaskSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ProjectService projectService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskView create(Long projectId, CreateTaskRequest request){
        if (request.getDueDate() != null &&
            request.getDueDate().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Due date cannot be in the past");
        }

        Task task = new Task();
        Project project = projectService.getProjectForCurrentUser(projectId);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setProject(project);
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());

        Task saved = taskRepository.save(task);

        return taskMapper.toView(saved);
    }

    public List<TaskView> getByProject(Long projectId){
        return taskRepository.findByProjectIdOrderByIdAsc(projectId).stream()
            .map(t -> taskMapper.toView(t))
            .toList();
    }

    public Page<TaskView> getByProject(
        Long projectId,
        int page,
        int size
    ){
        Pageable pageable = PageRequest.of(
            page, 
            size,
            Sort.by("id").descending()
        );

        Project project = projectService.getProjectForCurrentUser(projectId);
        Page<Task> tasks = taskRepository.findByProject(project, pageable);

        return tasks.map(taskMapper::toView);
    }

    public void updateStatus(Long projectId, Long taskId, TaskStatus taskStatus){
        Project project = projectService.getProjectForCurrentUser(projectId);
        Task task = taskRepository.findByIdAndProject(taskId, project)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        task.setStatus(taskStatus);
        taskRepository.save(task);
    }

    public void deleteTask(Long projectId, Long taskId){
        Project project = projectService.getProjectForCurrentUser(projectId);
        Task task = taskRepository.findByIdAndProject(taskId, project)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        taskRepository.delete(task);
    }

    public Page<TaskView> searchTasks(
        Long projectId,
        TaskStatus status,
        String title,
        int page,
        int size
    ){
        Project project = projectService.getProjectForCurrentUser(projectId);
        Pageable pageable = PageRequest.of(
            page, 
            size, 
            Sort.by("id").descending()
        );

        Specification<Task> spec = 
            Specification.where(TaskSpecifications.hasProject(project))
                        .and(TaskSpecifications.hasStatus(status))
                        .and(TaskSpecifications.titleContains(title));
        
        Page<Task> tasks = taskRepository.findAll(spec, pageable);
        return tasks.map(taskMapper::toView);

        //return taskRepository.findAll(spec, pageable);
    }

    public void updateTask(Long projectId, Long taskId, UpdateTaskRequest request){
        Project project = projectService.getProjectForCurrentUser(projectId);
        Task task = taskRepository.findByIdAndProject(taskId, project)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        taskMapper.updateTask(task, request);
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

    public TaskView getTask(Long projectId, Long taskId){
        Project project = projectService.getProjectForCurrentUser(projectId);
        Task task = taskRepository.findByIdAndProject(taskId, project)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return taskMapper.toView(task);
    }

    public UpdateTaskRequest getUpdateForm(Long projectId, Long taskId){
        Project project = projectService.getProjectForCurrentUser(projectId);
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
