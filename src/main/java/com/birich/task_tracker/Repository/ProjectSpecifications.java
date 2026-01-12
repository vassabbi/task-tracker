package com.birich.task_tracker.Repository;

import org.springframework.data.jpa.domain.Specification;

import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.User;

public class ProjectSpecifications {
    public static Specification<Project> ownedBy(User user){
        return (root, query, cb) ->
            cb.equal(root.get("owner"), user);
    }
}
