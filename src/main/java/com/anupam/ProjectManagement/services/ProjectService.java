package com.anupam.ProjectManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anupam.ProjectManagement.demo.Project;
import com.anupam.ProjectManagement.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		// Logic
		 return projectRepository.save(project);
	}
}
