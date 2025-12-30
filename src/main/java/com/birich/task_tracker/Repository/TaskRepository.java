package com.birich.task_tracker.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birich.task_tracker.Entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);
}