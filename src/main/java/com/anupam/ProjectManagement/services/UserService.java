package com.anupam.ProjectManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.anupam.ProjectManagement.demo.User;
import com.anupam.ProjectManagement.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

		//Username has to be unique (exception)

		// Make sure that password & confirmPassword match

		// We don't persist or show the confirm password
		return userRepository.save(newUser);
	}
	
}
