package com.saprykin.surveyapp.service;


import com.saprykin.surveyapp.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    List<User> findAllUsers();

    void deleteUserById(int id);

    User findUserById(int id);

    User findUserByEmail(String email);

    void updateUser(User user);
}
