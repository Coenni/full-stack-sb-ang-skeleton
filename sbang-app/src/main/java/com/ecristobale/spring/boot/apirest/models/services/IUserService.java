package com.ecristobale.spring.boot.apirest.models.services;

import com.ecristobale.spring.boot.apirest.models.entity.User;

public interface IUserService {

	public User findByUsername(String username);
}
