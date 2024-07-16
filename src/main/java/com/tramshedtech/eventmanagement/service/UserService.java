package com.tramshedtech.eventmanagement.service;


import com.tramshedtech.eventmanagement.entity.User;

import java.util.List;

public interface UserService {
    public User findByAccount(String account);

    boolean regis(User user);

    List<User> allUsers();
}
