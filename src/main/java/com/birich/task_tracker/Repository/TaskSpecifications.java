package com.birich.task_tracker.Repository;

import org.springframework.data.jpa.domain.Specification;

import com.birich.task_tracker.Entity.Task;

public class TaskSpecifications {
    public static Specification<Task> hasProject(Long projectId){
        return (root, query, cb) ->
            cb.equal(root.get("project").get("id"), projectId);
    }
}
