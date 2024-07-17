package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public User findByAccount(String account);

    boolean regis(User user);

    String findAvatar(int uid);

    List<User> allUsers();

    String getPwd(int uid);

    boolean updatePwd(String passWord, int uid);
}
