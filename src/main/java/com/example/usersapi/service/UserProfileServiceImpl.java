package com.example.usersapi.service;

import com.example.usersapi.exception.UserNotFoundException;
import com.example.usersapi.model.User;
import com.example.usersapi.model.UserProfile;
import com.example.usersapi.repository.UserProfileRepository;
import com.example.usersapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService{
    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserProfile createProfile(UserProfile userProfile, int userId) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(userId);

        UserProfile newUserProfile = userProfile;
        newUserProfile.setUserId(userId);
            if(foundUser.isPresent()) {
                foundUser.get().setUserProfile(newUserProfile);
            } else {
                // return null;
                throw new UserNotFoundException(HttpStatus.BAD_REQUEST, "User Not Found");
            }
        return userProfileRepository.save(userProfile);

    }

    @Override
    public UserProfile updateProfile(UserProfile userProfile, int userId) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(userId);

        UserProfile newUserProfile = userProfile;
        newUserProfile.setUserId(userId);
        if(foundUser.isPresent()) {
            foundUser.get().setUserProfile(newUserProfile);
            userRepository.save(foundUser.get());
        } else {
//            return null;
            throw new UserNotFoundException(HttpStatus.BAD_REQUEST, "User Not Found");
        }
        newUserProfile = userProfileRepository.save(userProfile);

        return newUserProfile;
    }

    @Override
    public UserProfile getUserProfile(int userId) {
        return userProfileRepository.getUserProfileByUserId(userId);
    }
}
