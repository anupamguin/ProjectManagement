package com.anupam.ProjectManagement.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.anupam.ProjectManagement.demo.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
	
	User findByUsername(String username);
	User getById(Long id);
}
