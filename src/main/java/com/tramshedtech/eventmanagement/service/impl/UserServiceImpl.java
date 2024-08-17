package com.tramshedtech.eventmanagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tramshedtech.eventmanagement.Vo.UserPositionAndDepartmentVo;
import com.tramshedtech.eventmanagement.Vo.UserVo;
import com.tramshedtech.eventmanagement.entity.CustomPage;
import com.tramshedtech.eventmanagement.entity.Department;
import com.tramshedtech.eventmanagement.entity.Position;
import com.tramshedtech.eventmanagement.entity.User;
import com.tramshedtech.eventmanagement.mapper.PositionMapper;
import com.tramshedtech.eventmanagement.mapper.UserMapper;
import com.tramshedtech.eventmanagement.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;



import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private PositionMapper positionMapper;

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

    @Override
    public UserPositionAndDepartmentVo findPositionDepartment(int uid) {
        UserPositionAndDepartmentVo positionAndDepartment = userMapper.findPositionDepartment(uid);
        return positionAndDepartment;
    }

    @Override
    public CustomPage findAllUser(int page, int size) {

        PageHelper.startPage(page, size);

        List<User> officeleavesList = userMapper.findAllUser();

        // 得到分页信息
        PageInfo<User> pageInfo = new PageInfo<>(officeleavesList);

        // 封装数据
        CustomPage customPage = new CustomPage()
                .setCurrentPage(page)
                .setSize(size)
                .setTotal(pageInfo.getTotal())
                .setTotalPage(pageInfo.getPages())
                .setPageData(officeleavesList);

        return customPage;
    }

    @Override
    public List<Department> findAllDepartment() {

        return userMapper.findAllDepartment();
    }

    @Override
    public List<Position> findAllPosition() {

        return userMapper.findAllPosition();

    }

    @Override
    public CustomPage search(CustomPage pages, User users) {

        PageHelper.startPage(pages.getCurrentPage(), pages.getSize());

        List<User> userList = userMapper.search(users);

        PageInfo<User> pageInfo = new PageInfo<>(userList);


        return new CustomPage(
                pages.getCurrentPage(),
                pages.getSize(),
                pageInfo.getTotal(),
                pageInfo.getPages(),
                pageInfo.getList()
        );
    }

    @Override
    public boolean addUser(UserVo user) {
        return userMapper.addUser(user);
    }

    @Override
    public UserVo findbyIdModify(int id) {
        return userMapper.findbyIdModify(id);
    }

    @Override
    public boolean findbyIdDel(int id) {
        return userMapper.findbyIdDel(id);
    }

    @Override
    public boolean modifyUser(UserVo user) {
        return userMapper.modifyUser(user);
    }

    @Override
    public List<User> allUsersExcel() {
        List<User> allUsers = userMapper.allUsersExcel();
        return allUsers;
    }

    @Override
    public Position findPositionById(int pid) {
        return positionMapper.findById(pid);
    }
}
