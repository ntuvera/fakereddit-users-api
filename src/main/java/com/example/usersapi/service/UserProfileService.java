package com.example.usersapi.service;

import com.example.usersapi.model.UserProfile;

public interface UserProfileService {
    public UserProfile createProfile(UserProfile userProfile, int userId);
    public UserProfile updateProfile(UserProfile userProfile, int userId);
    public UserProfile getUserProfile(int UserId);
}
