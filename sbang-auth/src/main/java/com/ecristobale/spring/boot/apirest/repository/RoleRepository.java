package com.ecristobale.spring.boot.apirest.repository;

import com.ecristobale.spring.boot.apirest.entity.Role;
import com.ecristobale.spring.boot.apirest.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByAuthority(String authority);
}
