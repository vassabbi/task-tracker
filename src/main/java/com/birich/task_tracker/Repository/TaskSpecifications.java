package com.birich.task_tracker.Repository;

import org.springframework.data.jpa.domain.Specification;

import com.birich.task_tracker.Entity.Task;
import com.birich.task_tracker.Entity.TaskStatus;

public class TaskSpecifications {
    public static Specification<Task> hasProject(Long projectId){
        return (root, query, cb) ->
            cb.equal(root.get("project").get("id"), projectId);
    }

    public static Specification<Task> hasStatus(TaskStatus status){
        return (root, query, cb) ->
            status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Task> titleContains(String title){
        return (root, query, cb) ->
            (title == null || title.isBlank())
                ? null
                : cb.like(
                    cb.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
                );
    }
}
