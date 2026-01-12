package com.birich.task_tracker.Service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.birich.task_tracker.Dto.CreateProjectRequest;
import com.birich.task_tracker.Dto.ProjectResponse;
import com.birich.task_tracker.Entity.Project;
import com.birich.task_tracker.Entity.User;
import com.birich.task_tracker.Repository.ProjectRepository;
import com.birich.task_tracker.Repository.ProjectSpecifications;
import com.birich.task_tracker.Repository.UserRepository;
import com.birich.task_tracker.Utils.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<ProjectResponse> findAllForCurrentUser(){
        String username = SecurityUtils.currentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        Specification<Project> spec =
                Specification.where(ProjectSpecifications.ownedBy(user));
        
        return projectRepository.findAll(spec).stream()
            .map(p -> new ProjectResponse(
                p.getId(),
                p.getName(),
                p.getDescription()
            ))
            .toList();
    }

    public Project getProjectForCurrentUser(Long projectId){
        String username = SecurityUtils.currentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        
        return projectRepository.findByIdAndOwner(projectId, user)
            .orElseThrow(() -> 
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Project not found"
                )
            );
    }

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
        String username = SecurityUtils.currentUsername();
        User owner = userRepository.findByUsername(username)
                .orElseThrow();

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwner(owner);
        Project saved = projectRepository.save(project);
        return new ProjectResponse(
            saved.getId(), 
            saved.getName(), 
            saved.getDescription()
        );
    }
}
