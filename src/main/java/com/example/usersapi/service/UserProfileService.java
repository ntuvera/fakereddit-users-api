package com.example.usersapi.service;

import com.example.usersapi.exception.UserNotFoundException;
import com.example.usersapi.model.UserProfile;

public interface UserProfileService {
    public UserProfile createProfile(UserProfile userProfile, int userId) throws UserNotFoundException;
    public UserProfile updateProfile(UserProfile userProfile, int userId) throws UserNotFoundException;
    public UserProfile getUserProfile(int UserId);
}
