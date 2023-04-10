package com.ecristobale.spring.boot.apirest.service;


import com.ecristobale.spring.boot.apirest.entity.Role;
import com.ecristobale.spring.boot.apirest.entity.User;
import com.ecristobale.spring.boot.apirest.repository.RoleRepository;
import com.ecristobale.spring.boot.apirest.repository.UserRepository;
import com.ecristobale.spring.boot.apirest.security.DefaultPasswordEncoderFactories;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService implements IUserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;


	@Override
	public List<UserDto> findAllUsers() {
		List<User> list = userRepository.findAll();
		return list.stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public UserDto findById(Long id) {
		return userRepository.findById(id).map(this::toDto)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public UserDto saveOrUpdateUser(UserDto userDto) {
		return toDto(userRepository.save(toEntity(userDto)));
	}

	private UserDto toDto(User user) {
		return new UserDto(
				user.getId(),
				user.getUsername(),
				user.getFirstname(),
				user.getLastname(),
				"",
				true, // TODO if self register false, otherwise should come from user.getEnabled(),
				user.getRoles().stream()
						.map(Role::getAuthority)
						.collect(Collectors.toList())
		);
	}

	private User toEntity(UserDto dto) {
		return new User(
				dto.getId(),
				dto.getUsername(),
				dto.getFirstname(),
				dto.getLastname(),
				!StringUtils.isEmpty(dto.getPassword()) && dto.getId() != null
						? userRepository.findById(dto.getId()).map(User::getPassword).orElseThrow(() -> new RuntimeException("User not found"))
						: passwordEncoder.encode(dto.getPassword()),
				dto.getEnabled(),
				Optional.ofNullable(dto.getRoles()).map(strings ->
						strings.stream().map(s -> roleRepository.findByAuthority(s).orElseThrow(() -> new RuntimeException("Role not found")))
						.collect(Collectors.toList())).orElse(Collections.emptyList())

		);
	}
}
