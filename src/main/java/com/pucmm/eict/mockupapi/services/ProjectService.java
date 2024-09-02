package com.pucmm.eict.mockupapi.services;

import com.pucmm.eict.mockupapi.models.Mock;
import com.pucmm.eict.mockupapi.models.Project;
import com.pucmm.eict.mockupapi.repositories.MockRepository;
import com.pucmm.eict.mockupapi.repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MockRepository mockRepository;
    private final MockService mockService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, MockRepository mockRepository, MockService mockService) {
        this.projectRepository = projectRepository;
        this.mockRepository = mockRepository;
        this.mockService = mockService;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getAllProjectsByUser(UUID id) {
        return projectRepository.findAllByUserId(id);
    }

    public Project getProjectById(UUID id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProjectAndMocks(UUID projectId) {
        Project project = projectRepository.findById(projectId);

        // Borrar los mocks asociados al proyecto
        List<Mock> mocks = mockService.getAllMocksByProjectId(projectId);
        for (Mock mock : mocks) {
            mockRepository.delete(mock);
        }

        // Finalmente, borrar el proyecto
        projectRepository.deleteById(projectId);
    }

    @Transactional
    public void deleteProjectById(UUID id) {
        projectRepository.deleteById(id);
    }
}
