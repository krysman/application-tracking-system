package com.saprykin.ats.util;

/*
В session должны быть thread-safe объекты, поетому поля объявлены final
 */
public class UserDetails {

    private final int userId;
    private final String role;

    public UserDetails(int userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", role='" + role + '\'' +
                '}';
    }
}
