package com.example.usersapi.service;

import com.example.usersapi.exception.NoMatchingUserFoundException;
import com.example.usersapi.exception.UserAlreadyExistsException;
import com.example.usersapi.exception.UserNotFoundException;
import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.model.UserRole;
import com.example.usersapi.repository.UserRepository;
import com.example.usersapi.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private ArrayList<User> userList;
    private User tempUser1;
    private User tempUser2;
    private User tempUser3;
    private User foundUser;
//    public PasswordEncoder encoder() {return new BCryptPasswordEncoder();};

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private UserRole userRole;

    @Mock
    private UserServiceImpl userServiceHelper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JwtResponse jwtResponse;

    @Before
    public void initUsers() {
        userList = new ArrayList<User>();
        tempUser1 = new User("batman", "bat", "iam@batman.com");
        tempUser2 = new User("robin", "bird", "iam@robin.com");
        tempUser3 = new User("alfred", "butler", "iam@allpowerful.com");
        tempUser1.setId(1);

        userList.add(tempUser1);
        userList.add(tempUser2);
        userList.add(tempUser3);
    }

    @Test
    public void signupUser_User_Success() throws UserAlreadyExistsException, UserNotFoundException {

        JwtResponse successResponse = new JwtResponse("fake-token-123", "batman", 1);

        when(userRoleService.getRole(anyString())).thenReturn(new UserRole("ROLE_USER"));
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("bat");
//        when(userService.getUser(anyString())).thenReturn(tempUser1);
//        when(userService.getUser(anyString())).thenReturn(null);
        when(userService.getUser(anyString())).thenReturn(tempUser1).thenReturn(null);
//        when(userService.getUser(anyString())).thenReturn(null).thenReturn(tempUser1);
        when(userRepository.save(any())).thenReturn(tempUser1);

        when(userService.loadUserByUsername(anyString())).thenReturn(tempUser1); // TODO: why wont this return the desired user?

        when(jwtUtil.generateToken(any())).thenReturn("fake-token-123");
        when(userRepository.findByUsername(anyString())).thenReturn(null).thenReturn(tempUser1);

        JwtResponse createdUserResponse = userService.signUpUser(tempUser1);

        assertNotNull(createdUserResponse);
        assertEquals(successResponse.getToken(), createdUserResponse.getToken());
        assertEquals(successResponse.getUsername(), createdUserResponse.getUsername());
        assertEquals(successResponse.getId(), createdUserResponse.getId());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void signupUser_User_Failure() throws UserAlreadyExistsException, UserNotFoundException {
        when(userService.getUser(tempUser1.getUsername())).thenReturn(tempUser1);

        JwtResponse failedSignUpResponse = userService.signUpUser(tempUser1);
    }

    @Test
    public void loginUser_User_Success() throws UserNotFoundException {
        when(userRepository.findByEmail(anyString())).thenReturn(tempUser1);
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(any())).thenReturn("fake-token-12345");

        JwtResponse successLoginResponse = userService.loginUser(tempUser1);
    }

    @Test(expected = UserNotFoundException.class)
    public void loginUser_User_Failure() throws UserNotFoundException {
        when(userRepository.findByEmail(tempUser1.getEmail())).thenReturn(null);

        JwtResponse failedLoginResponse = userService.loginUser(tempUser1);
    }

    @Test
    public void listAll_Users_Success() {
        int returnedListSize = 0;

        when(userRepository.findAll()).thenReturn(userList);

        Iterable<User> returnedList = userService.listAll();

        if (returnedList instanceof Collection) {
            returnedListSize = ((Collection<?>) returnedList).size();
        }

        assertNotNull(returnedList);
        assertEquals(userList.size(), returnedListSize);
        assertEquals(tempUser1, userList.get(0));
        assertEquals(tempUser2, userList.get(1));
        assertEquals(tempUser3, userList.get(2));
    }

    @Test
    public void findById_User_Success() throws NoMatchingUserFoundException {
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(tempUser1));

        foundUser = userService.findById(tempUser1.getId()).get();

        assertNotNull(foundUser);
        assertEquals(tempUser1.getUsername(), foundUser.getUsername());
        assertEquals(tempUser1.getEmail(), foundUser.getEmail());
        assertEquals(tempUser1.getPassword(), foundUser.getPassword());
    }

    @Test(expected = NoMatchingUserFoundException.class)
    public void findById_User_Failure() throws NoMatchingUserFoundException {
//        when(userRepository.findById(anyInt()).isPresent()).thenReturn(false);
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(tempUser1).empty());

        userService.findById(0);
    }

    @Test
    public void loadUserByUsername_User_Success() throws UserNotFoundException {
        when(userService.getUser(any())).thenReturn(tempUser1);

        foundUser = userService.loadUserByUsername(tempUser1.getUsername());

        assertNotNull(foundUser);
        assertNotNull(tempUser1.getUsername(), foundUser.getUsername());
        assertNotNull(tempUser1.getEmail(), foundUser.getEmail());
        assertNotNull(tempUser1.getPassword(), foundUser.getPassword());
    }

    @Test(expected = UserNotFoundException.class)
    public void loadUserByUsername_User_Failure() throws UserNotFoundException {
        when(userService.getUser(anyString())).thenReturn(null);
        foundUser = userService.loadUserByUsername(tempUser1.getUsername());
    }

    @Test
    public void getUser_User_Success() {
        when(userRepository.findByUsername(anyString())).thenReturn(tempUser1);

        foundUser = userService.getUser(tempUser1.getUsername());

        assertNotNull(foundUser);
        assertNotNull(tempUser1.getUsername(), foundUser.getUsername());
        assertNotNull(tempUser1.getEmail(), foundUser.getEmail());
        assertNotNull(tempUser1.getPassword(), foundUser.getPassword());

    }

}
