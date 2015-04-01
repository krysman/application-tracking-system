package com.saprykin.surveyapp.util;

public class UserDetails {

    private int userId;
    private String role;

    public UserDetails() {
    }

    public UserDetails(int userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", role='" + role + '\'' +
                '}';
    }
}
