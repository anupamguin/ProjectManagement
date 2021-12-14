package com.anupam.ProjectManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anupam.ProjectManagement.demo.Backlog;
import com.anupam.ProjectManagement.demo.Project;
import com.anupam.ProjectManagement.demo.User;
import com.anupam.ProjectManagement.exceptions.ProjectIdException;
import com.anupam.ProjectManagement.repositories.BacklogRepository;
import com.anupam.ProjectManagement.repositories.ProjectRepository;
import com.anupam.ProjectManagement.repositories.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Project saveOrUpdateProject(Project project,String username) {
		
		try {
			  User user=userRepository.findByUsername(username);
			  project.setUser(user);
			  project.setProjectLeader(user.getUsername());
			
			  project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId() == null) {
				
			   Backlog backlog=new Backlog();
			   project.setBacklog(backlog);
			   backlog.setProject(project);
			   backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			if(project.getId() != null)
			{
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
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
	
	public void deleteProjectByIdentifier(String projectId)
	{
		Project project = projectRepository.findByProjectIdentifier(projectId);
		if(project == null)
			throw new ProjectIdException("Cannot Project with Id '"+projectId+"' .This Project does not exist");
		
		projectRepository.delete(project);
	}
}
