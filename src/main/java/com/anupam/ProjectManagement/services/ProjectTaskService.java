package com.anupam.ProjectManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anupam.ProjectManagement.demo.Backlog;
import com.anupam.ProjectManagement.demo.ProjectTask;
import com.anupam.ProjectManagement.repositories.BacklogRepository;
import com.anupam.ProjectManagement.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	BacklogRepository backlogRepository;

	@Autowired
	ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

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
	}
}
