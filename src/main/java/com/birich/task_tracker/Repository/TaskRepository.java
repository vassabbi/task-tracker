package com.birich.task_tracker.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.Task;

public interface TaskRepository 
    extends JpaRepository<Task, Long>, 
            JpaSpecificationExecutor<Task>{

    List<Task> findByProjectId(Long projectId);
    List<Task> findByProjectIdOrderByIdAsc(Long projectId);
    Page<Task> findByProjectId(
        Long projectId,
        Pageable pageable
    );
    Optional<Task> findByIdAndProject(Long id, Project project);
}