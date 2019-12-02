package com.example.usersapi.service;

import com.example.usersapi.exception.UserRoleExistsException;
import com.example.usersapi.exception.InvalidArgumentException;
import com.example.usersapi.model.UserRole;
import com.example.usersapi.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static org.springframework.http.HttpStatus.*;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole createRole(UserRole newRole) throws UserRoleExistsException, InvalidArgumentException {
        if(userRoleRepository.findByName(newRole.getName()) != null) {
            throw new UserRoleExistsException(BAD_REQUEST, "This role is already registered");
        }
        if(newRole.getName().trim() == "") {
            throw new InvalidArgumentException(BAD_REQUEST, "Role Name cannot be blank");
        }

        return userRoleRepository.save(newRole);
    }

    @Override
    public UserRole getRole(String roleName) {
        return userRoleRepository.findByName(roleName);
    }
}
