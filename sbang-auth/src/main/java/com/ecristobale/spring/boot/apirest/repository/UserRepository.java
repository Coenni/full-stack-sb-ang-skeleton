package com.ecristobale.spring.boot.apirest.repository;

import com.ecristobale.spring.boot.apirest.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
}
