package com.anupam.ProjectManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anupam.ProjectManagement.demo.Backlog;
import com.anupam.ProjectManagement.demo.Project;
import com.anupam.ProjectManagement.demo.ProjectTask;
import com.anupam.ProjectManagement.exceptions.ProjectNotFoundException;
import com.anupam.ProjectManagement.repositories.BacklogRepository;
import com.anupam.ProjectManagement.repositories.ProjectRepository;
import com.anupam.ProjectManagement.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	BacklogRepository backlogRepository;

	@Autowired
	ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		try {
// ProjectServiceTask to be added to a specific project ,project !=null, Backlog exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
// set the Backlog to ProjectTask
		projectTask.setBacklog(backlog);
// We want our project sequence be like this: IDPRO-1 , IDPRO-2 ...100,101
		Integer BacklogSequence = backlog.getPTSequence();

// update the Backlog sequence
		BacklogSequence++;
		backlog.setPTSequence(BacklogSequence);
// Add sequence to Project Task
		projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
// Initial Priority when Priority null
		if (projectTask.getPriority() == null) { // In future we need projectTask.getPriority() == 0 to handle the form
			projectTask.setPriority(3);
		}
// Initial status when status is null
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}

		return projectTaskRepository.save(projectTask);
	 }catch(Exception e) {
		 throw new ProjectNotFoundException("Project Not Found");
	 }
	}

	
	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		
		Project project=projectRepository.findByProjectIdentifier(backlog_id);
		if(project == null)
		{
			throw new ProjectNotFoundException("Project with Id: "+backlog_id+" does not exists");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}
}
