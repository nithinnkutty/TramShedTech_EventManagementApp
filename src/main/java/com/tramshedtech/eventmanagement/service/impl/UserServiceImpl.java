package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.entity.User;
import com.tramshedtech.eventmanagement.mapper.UserMapper;
import com.tramshedtech.eventmanagement.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User findByAccount(String account) {

        return userMapper.findByAccount(account);
    }

    public boolean regis(User user) {
        boolean r = userMapper.regis(user);
        return r;
    }

    @Override
    public List<User> allUsers() {
        List<User> allUsers = userMapper.allUsers();
        return allUsers;
    }


}
