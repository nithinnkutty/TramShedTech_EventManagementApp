package com.tramshedtech.eventmanagement.service;


import com.tramshedtech.eventmanagement.Vo.UserPositionAndDepartmentVo;
import com.tramshedtech.eventmanagement.entity.User;
import com.tramshedtech.eventmanagement.entity.CustomPage;

import java.util.List;

public interface UserService {
    public User findByAccount(String account);

    boolean regis(User user);

    String findAvatar(int uid);

    List<User> allUsers();

    String getPwd(int uid);

    boolean updatePwd(String passWord, int uid);

    User findbyId(int uid);

    boolean updateInfo(User user, int uid);

    UserPositionAndDepartmentVo findPositionDepartment(int uid);

    CustomPage findAllUser(int page, int size);
}
