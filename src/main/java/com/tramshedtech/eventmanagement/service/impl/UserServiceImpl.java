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
    public String findAvatar(int uid) {
        String avatar = userMapper.findAvatar(uid);
        return avatar;
    }

    @Override
    public List<User> allUsers() {
        List<User> allUsers = userMapper.allUsers();
        return allUsers;
    }

    @Override
    public String getPwd(int uid) {
        String passWord = userMapper.getPwd(uid);
        return passWord;
    }

    @Override
    public boolean updatePwd(String passWord, int uid) {
        boolean r = userMapper.updatePwd(passWord,uid);
        return r;
    }

    @Override
    public User findbyId(int uid) {
        return userMapper.findbyId(uid);
    }

    @Override
    public boolean updateInfo(User user, int uid) {
        boolean r = userMapper.updateInfo(user,uid);
        return r;
    }


}
