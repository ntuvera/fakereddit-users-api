package com.example.usersapi.model;

public class JwtResponse {
    private String token;
    private String username;
    private int id;

    public JwtResponse(String token, String username, int id) {
        this.token = token;
        this.username = username;
        this.id = id;
    }

    public JwtResponse() { }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
