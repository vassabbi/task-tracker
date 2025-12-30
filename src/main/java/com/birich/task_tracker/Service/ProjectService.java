package com.birich.task_tracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.birich.task_tracker.Dto.CreateProjectRequest;
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

    public ProjectResponse create(CreateProjectRequest request){
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        Project saved = projectRepository.save(project);
        return new ProjectResponse(
            saved.getId(), 
            saved.getName(), 
            saved.getDescription()
        );
    }
}
