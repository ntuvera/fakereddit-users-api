package com.example.usersapi.service;

import com.example.usersapi.exception.UserRoleExistsException;
import com.example.usersapi.model.UserRole;
import com.example.usersapi.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole createRole(UserRole newRole) throws UserRoleExistsException {
        if(userRoleRepository.findByName(newRole.getName()) != null){
            throw new UserRoleExistsException(HttpStatus.BAD_REQUEST, "This role is already registered");
        }
        return userRoleRepository.save(newRole);
    }

    @Override
    public UserRole getRole(String roleName) {
        return userRoleRepository.findByName(roleName);
    }
}
