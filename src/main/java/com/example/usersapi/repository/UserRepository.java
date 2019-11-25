package com.example.usersapi.repository;

import com.example.usersapi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("FROM User u WHERE u.username = ?1")
    public User findByUsername(String username);

    @Query("FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

}
