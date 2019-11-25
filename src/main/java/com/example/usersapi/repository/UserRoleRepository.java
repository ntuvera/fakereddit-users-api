package com.example.usersapi.repository;

import com.example.usersapi.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    public UserRole findByName(String name);
}
