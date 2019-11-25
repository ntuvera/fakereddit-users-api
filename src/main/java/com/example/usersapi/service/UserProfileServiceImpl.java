package com.example.usersapi.service;

import com.example.usersapi.model.User;
import com.example.usersapi.model.UserProfile;
import com.example.usersapi.repository.UserProfileRepository;
import com.example.usersapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService{
    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserProfile createProfile(UserProfile userProfile, int userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        UserProfile newUserProfile = userProfile;
        newUserProfile.setUserId(userId);
            if(foundUser.isPresent()) {
                foundUser.get().setUserProfile(newUserProfile);
            } else {
                return null;
            }
        return userProfileRepository.save(userProfile);

    }

    @Override
    public UserProfile updateProfile(UserProfile userProfile, int userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        UserProfile newUserProfile = userProfile;
        newUserProfile.setUserId(userId);
        if(foundUser.isPresent()) {
            foundUser.get().setUserProfile(newUserProfile);
            userRepository.save(foundUser.get());
        } else {
            return null;
        }
        newUserProfile = userProfileRepository.save(userProfile);

        return newUserProfile;
    }

    @Override
    public UserProfile getUserProfile(int userId) {
        return userProfileRepository.getUserProfileByUserId(userId);
    }
}
