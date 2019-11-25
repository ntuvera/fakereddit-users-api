package com.example.usersapi.model;

import javax.persistence.*;

@Entity
@Table(name="user_profiles")
public class UserProfile {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="additional_email")
    private String additionalEmail;

    @Column
    private String mobile;

    @Column
    private String address;

    @Column(name="user_id", nullable = false)
    private int userId;

    public UserProfile() { }

    public UserProfile(String additionalEmail, String mobile, String address, int userId) {
        this.additionalEmail = additionalEmail;
        this.mobile = mobile;
        this.address = address;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdditionalEmail() {
        return additionalEmail;
    }

    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = additionalEmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
