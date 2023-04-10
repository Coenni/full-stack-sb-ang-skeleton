package com.ecristobale.spring.boot.apirest.service;


import java.util.List;

public interface IUserService {
    List<UserDto> findAllUsers();

    UserDto findById(Long id);

    UserDto saveOrUpdateUser(UserDto userDto);
}
