package com.example.demohealth.dao;

import com.example.demohealth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yifancai
 */
public interface IUserDao extends JpaRepository<User,Integer> {
    List<User> findAllByUsernameAndPassword(String username, String password);

    List<User> findAllByUsername(String username);
    List<User> findAllByTelephone(String telephone);

    List<User> findAllByUsernameAndTelephone(String username,String telephone);
}

