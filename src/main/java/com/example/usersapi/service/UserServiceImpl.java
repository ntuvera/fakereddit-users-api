package com.example.usersapi.service;

import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.model.UserRole;
import com.example.usersapi.repository.UserRepository;
import com.example.usersapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Bean
    public PasswordEncoder encoder() {return new BCryptPasswordEncoder(); }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public JwtResponse signUpUser(User newUser) {
        JwtResponse signupResponse = new JwtResponse();
        UserRole userRole = null;

        if (newUser.getUserRole() != null) {
            userRole = userRoleService.getRole(newUser.getUserRole().getName());
        } else {
            userRole = userRoleService.getRole("ROLE_USER");
        }

        newUser.setUserRole(userRole);
        newUser.setPassword(encoder().encode(newUser.getPassword()));

        if (userRepository.save(newUser) != null) {
            User user = loadUserByUsername(newUser.getUsername());
            signupResponse.setToken(jwtUtil.generateToken(user));
            signupResponse.setUsername(newUser.getUsername());
            signupResponse.setId(newUser.getId());

            System.out.println(signupResponse);

            return signupResponse;
        }
        return null;
    }

    @Override
    public JwtResponse loginUser(User user) {
        JwtResponse loginResponse = new JwtResponse();
        User foundUser = userRepository.findByEmail(user.getEmail());

        if (foundUser != null && encoder()
                .matches(user.getPassword(), foundUser.getPassword())) {
            loginResponse.setToken(jwtUtil.generateToken(foundUser));
            loginResponse.setUsername(foundUser.getUsername());
            loginResponse.setId(foundUser.getId());
            return loginResponse;
        }
        return null;
    }

    @Override
    public Iterable<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int userId) {
        if(userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId);
        }
        return null;
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User loadUserByUsername(String username) {
        User user = getUser(username);

        if (user != null) {
            return user;
        }

        return null;
    }
}
