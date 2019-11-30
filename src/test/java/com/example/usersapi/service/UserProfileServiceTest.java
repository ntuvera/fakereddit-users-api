package com.example.usersapi.service;

import com.example.usersapi.exception.UserNotFoundException;
import com.example.usersapi.model.User;
import com.example.usersapi.model.UserProfile;
import com.example.usersapi.repository.UserProfileRepository;
import com.example.usersapi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {
    private UserProfile tempProfile;
    private User tempUser;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @InjectMocks
    private UserProfile userProfile;

    @Mock
    User user;

    @Mock
    UserProfileRepository userProfileRepository;

    @Mock
    UserRepository userRepository;

    @Before
    public void initProfile() {
        tempProfile = new UserProfile("iam@darknight.com", "1234567890", "batcave", 1);
        tempUser = new User();
        tempUser.setId(1);
        tempUser.setUsername("batman");
        tempUser.setPassword("bat");
    }

    @Test
    public void createProfile_UserProfile_Success() throws UserNotFoundException {
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(tempUser));
        when(userProfileRepository.save(any())).thenReturn(tempProfile);

        UserProfile createdProfile = userProfileService.createProfile(tempProfile, tempUser.getId());

        assertEquals(tempProfile, createdProfile);
    }

    @Test(expected = UserNotFoundException.class)
    public void createProfile_UserNotFound_Failure() throws UserNotFoundException {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        UserProfile createdProfile = userProfileService.createProfile(tempProfile, tempUser.getId());
    }

    @Test
    public void updateProfile_UserProfile_Success() throws UserNotFoundException {
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(tempUser));
        when(userProfileRepository.save(any())).thenReturn(tempProfile);

        UserProfile updatedProfile = userProfileService.updateProfile(tempProfile, tempUser.getId());

        assertEquals(tempProfile, updatedProfile);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateProfile_UserNotFound_Failure() throws UserNotFoundException {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        UserProfile updatedProfile = userProfileService.createProfile(tempProfile, tempUser.getId());
    }

    @Test
    public void getUserProfile_UserProfile_Success() {
        when(userProfileRepository.getUserProfileByUserId(anyInt())).thenReturn(tempProfile);

        UserProfile retrievedProfile = userProfileService.getUserProfile(tempProfile.getUserId());

        assertEquals(tempProfile, retrievedProfile);
        assertEquals(tempProfile.getAdditionalEmail(), retrievedProfile.getAdditionalEmail());
        assertEquals(tempProfile.getMobile(), retrievedProfile.getMobile());
        assertEquals(tempProfile.getAddress(), retrievedProfile.getAddress());
    }

}
