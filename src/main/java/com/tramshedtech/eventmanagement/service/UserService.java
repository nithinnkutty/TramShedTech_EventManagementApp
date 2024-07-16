package com.tramshedtech.eventmanagement.service;


import com.tramshedtech.eventmanagement.entity.User;

public interface UserService {
    public User findByAccount(String account);

    boolean regis(User user);

    String findAvatar(int uid);
}
