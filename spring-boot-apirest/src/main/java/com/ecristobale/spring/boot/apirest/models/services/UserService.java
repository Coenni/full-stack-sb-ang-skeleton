package com.ecristobale.spring.boot.apirest.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecristobale.spring.boot.apirest.models.dao.IUserDao;
import com.ecristobale.spring.boot.apirest.models.entity.User;

@Service
public class UserService implements UserDetailsService, IUserService {

	@Autowired
	private IUserDao userDao;
	
	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		
		if(user == null) {
			log.error("Error in login: user: '" + username + "' does not exist");
			throw new UsernameNotFoundException("Error in login: user: '" + username + "' does not exists");
		}
		
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.peek(authority -> log.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
