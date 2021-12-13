package com.anupam.ProjectManagement.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anupam.ProjectManagement.demo.Project;
import com.anupam.ProjectManagement.demo.User;
import com.anupam.ProjectManagement.services.MapValidationErrorService;
import com.anupam.ProjectManagement.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	MapValidationErrorService mapValidationErrorService;

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult){
		// Validate passwords match
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);
		if(errorMap != null) return errorMap;

		User newUser=userService.saveUser(user);

		return new ResponseEntity<User>(newUser,HttpStatus.CREATED);
	}
}
