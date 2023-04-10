package com.ecristobale.spring.boot.apirest.controllers;

import com.ecristobale.spring.boot.apirest.entity.User;
import com.ecristobale.spring.boot.apirest.service.IUserService;
import com.ecristobale.spring.boot.apirest.service.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final IUserService userService;
    @GetMapping("/me")
    public Principal get(final Principal principal) {
        return principal;
    }

    @GetMapping
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(path = "/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping(path = "/{id}")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.saveOrUpdateUser(userDto);
    }

    @PostMapping(path = "/register") // TODO should be fixed not public atm
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.saveOrUpdateUser(userDto);
    }

}
