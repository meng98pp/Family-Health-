package com.example.demohealth.service;

import com.example.demohealth.model.User;

import java.util.List;

public interface IUserService {
    public List<User> register_check(String username);

    public List<User> login(String username);

    public void register(User user) throws Exception;

    public List<User> findId(String username);

    public void save(User user);
}
