package com.tramshedtech.eventmanagement.service;


import com.tramshedtech.eventmanagement.Vo.UserPositionAndDepartmentVo;
import com.tramshedtech.eventmanagement.Vo.UserVo;
import com.tramshedtech.eventmanagement.entity.Department;
import com.tramshedtech.eventmanagement.entity.Position;
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

    List<Department> findAllDepartment();

    List<Position> findAllPosition();

    CustomPage search(CustomPage pages, User users);

    boolean addUser(UserVo user);

    UserVo findbyIdModify(int id);

    boolean findbyIdDel(int id);

    boolean modifyUser(UserVo user);
}
