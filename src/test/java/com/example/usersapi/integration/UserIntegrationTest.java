package com.example.usersapi.integration;

import com.example.usersapi.model.UserProfile;
import com.example.usersapi.repository.UserProfileRepository;
import com.example.usersapi.repository.UserRepository;
import org.junit.After;
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

import java.util.Optional;

import static org.junit.Assert.*;

@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIntegrationTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private UserRole createUserRole() {
        UserRole userRole = userRoleRepository.findByName("ROLE_ADMIN");

        if(userRole == null){
            userRole = new UserRole();
            userRole.setName("ROLE_ADMIN");
            userRole = userRoleRepository.save(userRole);
        }

        return userRole;
    }

    private User createUser() {
        UserRole userRole = createUserRole();

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("testUser@testmail.com");
        user.setUserRole(userRole);

        return user;
    }

    private UserProfile createUserProfile(User user) {
        UserProfile userProfile = new UserProfile();

        userProfile.setId(1);
        userProfile.setAdditionalEmail("testUser@fakemail.com");
        userProfile.setAddress("Test Address St.");
        userProfile.setMobile("123-FAKE-NUM");
        userProfile.setUser(java.util.Optional.ofNullable(user));
        userProfile.setUserId(user.getId());

        return userProfile;
    }

    @After
    public void sanitizeDb(){
        User foundUser = userRepository.findByUsername("testUser");
        if (foundUser != null)
            userRepository.delete(foundUser);

        UserRole foundUserRole = userRoleRepository.findByName(("ROLE_ADMIN"));
        if (foundUserRole != null)
            userRoleRepository.delete(foundUserRole);

        Optional<UserProfile> foundUserProfile = userProfileRepository.findById(1);
        if (foundUserProfile.isPresent())
            userProfileRepository.deleteById(foundUserProfile.get().getId());
    }

    @Test
    public void signup_User_Success() {
        User user = userRepository.findByUsername("testUser");

        if (user != null) {
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

    @Test
    public void deleteById_User_Success(){
        User user = userRepository.findByUsername("testUser");

        if (user == null) {
            user = createUser();
            userRepository.save(user);
        }

        userRepository.deleteById(user.getId());
        User foundUser = userRepository.findById(user.getId()).orElse(null);

        assertNull(foundUser);
    }

    @Test
    public void createUserProfile_User_Success() {
        User user = userRepository.findByUsername("testUser");

        if(user == null) {
            user = createUser();
            user = userRepository.save(user);
        }

        assertNotNull(user);
        assertNull(user.getUserProfile());

        user.setUserProfile(userProfileRepository.save(createUserProfile(user)));

        assertNotNull(user.getUserProfile());
        assertEquals("testUser@fakemail.com", user.getUserProfile().getAdditionalEmail());
        assertEquals("Test Address St.", user.getUserProfile().getAddress());
        assertEquals("123-FAKE-NUM", user.getUserProfile().getMobile());
    }
}
