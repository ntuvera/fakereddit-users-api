package com.example.usersapi.integration;

import com.example.usersapi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.usersapi.repository.UserRoleRepository;
import com.example.usersapi.model.User;
import com.example.usersapi.model.UserRole;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIntegrationTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    private UserRole createUserRole(){
        UserRole userRole = userRoleRepository.findByName("ROLE_ADMIN");

        if(userRole == null){
            userRole = new UserRole();
            userRole.setName("ROLE_ADMIN");
            userRole = userRoleRepository.save(userRole);
        }

        return userRole;
    }

    private User createUser(){
        UserRole userRole = createUserRole();

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("testUser@testmail.com");
        user.setUserRole(userRole);

        return user;
    }

    @Test
    public void signup_User_Success() {
        User user = userRepository.findByUsername("testUser");

        if(user != null) {
            userRepository.delete(user);
        }

        user = createUser();
        user = userRepository.save(user);
        User foundUser = userRepository.findByUsername(user.getUsername());

        assertNotNull(user);
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());

        userRepository.delete(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void signup_DuplicateUsername_Exception() {
        User user = createUser();

        userRepository.save(user);

        user.setId(0);

        userRepository.save(user);
    }
}
