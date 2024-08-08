package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.Vo.UserPositionAndDepartmentVo;
import com.tramshedtech.eventmanagement.Vo.UserVo;
import com.tramshedtech.eventmanagement.entity.Department;
import com.tramshedtech.eventmanagement.entity.Position;
import com.tramshedtech.eventmanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    public User findByAccount(String account);

    boolean regis(User user);

    String findAvatar(int uid);

    List<User> allUsers();

    String getPwd(int uid);

    boolean updatePwd(String passWord, int uid);

    User findbyId(int uid);

    boolean updateInfo(@Param("user")User user, @Param("uid")int uid);

    UserPositionAndDepartmentVo findPositionDepartment(int uid);

    List<User> findAllUser();

    List<Department> findAllDepartment();

    List<Position> findAllPosition();

    List<User> search(User users);

    boolean addUser(UserVo user);
}
