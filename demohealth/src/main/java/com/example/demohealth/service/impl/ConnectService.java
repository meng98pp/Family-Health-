package com.example.demohealth.service.impl;

import com.example.demohealth.dao.IConnectDao;
import com.example.demohealth.model.Connect;
import com.example.demohealth.service.IConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectService implements IConnectService {

    @Autowired
    IConnectDao connectDao;

    @Override
    public List<Connect> findByChild(String username) {
        return connectDao.findAllByUsernameChild(username);
    }

    @Override
    public List<Connect> findByParent(String username) {
        return connectDao.findAllByUsernameParent(username);
    }

    @Override
    public void save(Connect connect) {
        connectDao.save(connect);
    }
}