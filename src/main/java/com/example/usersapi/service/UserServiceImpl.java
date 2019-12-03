package com.example.usersapi.service;

import com.example.usersapi.exception.InvalidArgumentException;
import com.example.usersapi.exception.NoMatchingUserFoundException;
import com.example.usersapi.exception.UserAlreadyExistsException;
import com.example.usersapi.exception.UserNotFoundException;
import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.model.UserRole;
import com.example.usersapi.repository.UserRepository;
import com.example.usersapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public JwtResponse signUpUser(User newUser) throws UserAlreadyExistsException, UserNotFoundException, InvalidArgumentException {
        JwtResponse signupResponse = new JwtResponse();
        UserRole userRole = null;

        if (newUser.getUserRole() != null) {
            userRole = userRoleService.getRole(newUser.getUserRole().getName());
        } else {
            userRole = userRoleService.getRole("ROLE_USER");
        }

        newUser.setUserRole(userRole);
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        if(newUser.getUsername().trim() == "" || newUser.getPassword().trim() == "" || newUser.getEmail().trim() == "" )
            throw new InvalidArgumentException(HttpStatus.BAD_REQUEST, "Input must contain valid alphanumeric characters");

        if(!newUser.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            throw new InvalidArgumentException(HttpStatus.BAD_REQUEST, "Email must be of a valid format");

        if (getUser(newUser.getUsername())!= null) {
            throw new UserAlreadyExistsException(HttpStatus.BAD_REQUEST, "User with that name/email already Exists");
        }
        if (userRepository.save(newUser) != null) {
            User user = loadUserByUsername(newUser.getUsername());
            signupResponse.setToken(jwtUtil.generateToken(user));
            signupResponse.setUsername(newUser.getUsername());
            signupResponse.setId(newUser.getId());

            return signupResponse;
        } else {
            throw new UserAlreadyExistsException(HttpStatus.BAD_REQUEST, "User with that name/email already Exists");
        }
    }

    @Override
    public JwtResponse loginUser(User user) throws UserNotFoundException, InvalidArgumentException {
        JwtResponse loginResponse = new JwtResponse();
        if(user.getPassword().trim() == "" || user.getEmail().trim() == "" )
            throw new InvalidArgumentException(HttpStatus.BAD_REQUEST, "Input must contain valid alphanumeric characters");

        if(!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            throw new InvalidArgumentException(HttpStatus.BAD_REQUEST, "Email must be of a valid format");

        User foundUser = userRepository.findByEmail(user.getEmail());

        if (foundUser != null && bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            loginResponse.setToken(jwtUtil.generateToken(foundUser));
            loginResponse.setUsername(foundUser.getUsername());
            loginResponse.setId(foundUser.getId());
            return loginResponse;
        }
        throw new UserNotFoundException(HttpStatus.UNAUTHORIZED, "Invalid Username/Password");
    }

    @Override
    public Iterable<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int userId) throws NoMatchingUserFoundException {
        if(userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId);
        }
        throw new NoMatchingUserFoundException(HttpStatus.NOT_FOUND, "The user you're looking for does not exist");
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User loadUserByUsername(String username) throws UserNotFoundException {
        User user = getUser(username);

        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("No User Found with that Username");
        }
    }
}
