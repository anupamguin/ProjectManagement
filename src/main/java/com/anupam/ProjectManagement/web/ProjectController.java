package com.anupam.ProjectManagement.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anupam.ProjectManagement.demo.Project;
import com.anupam.ProjectManagement.services.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult)
	{ /* if we are not use @Valid then for error input from postman, in console has error & BindingResult catch the
	 	 error, it has error then it show the message */
		
		if(bindingResult.hasErrors()) {
		//	return new ResponseEntity<String>("Invalid Project Object",HttpStatus.BAD_REQUEST);
		//	return new ResponseEntity<List<FieldError>>(bindingResult.getFieldErrors(),HttpStatus.BAD_REQUEST);
		/* now for the above line ,	for empty object in postman body we get error */
			
			Map<String,String> errorMap=new HashMap<>();
			for(FieldError error: bindingResult.getFieldErrors())
			{
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
		}	
		Project project1 = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project1,HttpStatus.CREATED);
	}
}
