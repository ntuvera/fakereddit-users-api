package com.example.usersapi.repository;

import com.example.usersapi.model.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends CrudRepository <UserProfile, Integer> {
    @Query(value = "SELECT * FROM user_profiles up WHERE up.user_id =:userId ORDER BY up.id DESC LIMIT 1", nativeQuery=true)
    public UserProfile getUserProfileByUserId(int userId);
}
