package com.birich.task_tracker.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.User;

public interface ProjectRepository extends JpaRepository<Project, Long>,
                                           JpaSpecificationExecutor<Project>{
 
    Optional<Project> findByIdAndOwner(Long id, User owner);
}
