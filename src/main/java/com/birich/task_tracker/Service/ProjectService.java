package com.birich.task_tracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.birich.task_tracker.Dto.ProjectResponse;
import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectResponse> findAll(){
        return projectRepository.findAll().stream()
            .map(p -> new ProjectResponse(
                p.getId(),
                p.getName(),
                p.getDescription()
            ))
            .toList();
    }

    public Project create(Project project){
        return projectRepository.save(project);
    }
}
