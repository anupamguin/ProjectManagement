package com.anupam.ProjectManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anupam.ProjectManagement.demo.Project;
import com.anupam.ProjectManagement.exceptions.ProjectIdException;
import com.anupam.ProjectManagement.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);

		} catch (Exception e) {
			throw new ProjectIdException(
					"Project '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}
	}
	
	public Project findByProjectIdentifier(String projectId)
	{
		Project project = projectRepository.findByProjectIdentifier(projectId);
		if(project == null)
		 throw new ProjectIdException("Project Id: "+projectId+" does not exists");
		
		return project; 
	}
	
	public Iterable<Project> findAllProjects()
	{		
		return projectRepository.findAll();	
	}
}
