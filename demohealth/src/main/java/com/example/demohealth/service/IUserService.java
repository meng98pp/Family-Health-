package com.example.demohealth.service;

import com.example.demohealth.model.User;

import java.util.List;

public interface IUserService {
    public List<User> register_check(String username);

    public List<User> login(String username, String password);

    public void register(User user) throws Exception;
}
