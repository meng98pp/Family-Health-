package com.example.demohealth.service.impl;

import com.example.demohealth.dao.IUserDao;
import com.example.demohealth.model.User;
import com.example.demohealth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserDao userDao;


    @Override
    public List<User> login(String username, String password) {
        return userDao.findAllByUsernameAndPassword(username, password);
    }

    @Override
    public List<User> register_check(String username) {
        return userDao.findAllByUsername(username);
    }

    /**
     * 注册
     */
    @Override
    public void register(User user){
        userDao.save(user);
    }
}
