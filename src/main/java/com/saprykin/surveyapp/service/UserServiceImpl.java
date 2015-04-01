package com.saprykin.surveyapp.service;

import java.util.List;

import com.saprykin.surveyapp.dao.UserDao;
import com.saprykin.surveyapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
@Transactional //starts a transaction on each method start, and commits it on each method exit ( or rollback if method was failed due to an error).
// Note that since the transaction are on method scope, and inside method we are using DAO, DAO method will be executed within same transaction
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao dao;

    @Override
    public void saveUser(User user) {
        dao.saveUser(user);
    }

    @Override
    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    @Override
    public void deleteUserById(int id) {
        dao.deleteUserById(id);
    }

    @Override
    public User findUserById(int id) {
        return dao.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return dao.findUserByEmail(email);
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }


}
