package com.example.demohealth.dao;


import com.example.demohealth.model.Connect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IConnectDao extends JpaRepository<Connect,Integer> {

    List<Connect> findAllByUsernameParent(String username_parent);

    List<Connect> findAllByUsernameChild(String username_child);

}
