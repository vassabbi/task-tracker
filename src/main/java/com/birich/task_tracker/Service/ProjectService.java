package com.birich.task_tracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    public Project create(Project project){
        return projectRepository.save(project);
    }
}
