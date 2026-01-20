package com.birich.task_tracker.Dto.task;

import org.springframework.stereotype.Component;

import com.birich.task_tracker.Entity.Task;

@Component
public class TaskMapper {

    public TaskView toView(Task task){
        return new TaskView(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.getPriority(),
            task.getDueDate(),
            task.getCreatedAt()
        );
    }

    public void updateTask(Task task, UpdateTaskRequest request){
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
    }
}
