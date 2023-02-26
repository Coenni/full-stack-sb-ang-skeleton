package com.ecristobale.spring.boot.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ecristobale.spring.boot.apirest.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long>{

	public User findByUsername(String username);
	
//	@Query("SELECT u FROM Usuario WHERE u.username = ?1")
//	public Usuario findByUsername2(String username);
}
