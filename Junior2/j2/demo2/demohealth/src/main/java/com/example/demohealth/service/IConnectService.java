package com.example.demohealth.service;

import com.example.demohealth.model.Connect;

import java.util.List;

public interface IConnectService {

    public List<Connect> findByChild(String username);

    public List<Connect> findByParent(String username);

    public void save(Connect connect);
}
