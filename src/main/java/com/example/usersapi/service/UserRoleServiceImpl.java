package com.example.usersapi.service;

import com.example.usersapi.model.UserRole;
import com.example.usersapi.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.MissingFormatArgumentException;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole createRole(UserRole newRole) {
        if(newRole.getName() == null || newRole.getName() == "")
            throw new MissingFormatArgumentException("User Role name is invalid");

        return userRoleRepository.save(newRole);
    }

    @Override
    public UserRole getRole(String roleName) {
        return userRoleRepository.findByName(roleName);
    }
}
