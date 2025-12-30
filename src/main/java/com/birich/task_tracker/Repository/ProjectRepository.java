package com.birich.task_tracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birich.task_tracker.Entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
