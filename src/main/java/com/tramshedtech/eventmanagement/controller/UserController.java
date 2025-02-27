package com.tramshedtech.eventmanagement.controller;



import com.tramshedtech.eventmanagement.Vo.UserPositionAndDepartmentVo;
import com.tramshedtech.eventmanagement.Vo.UserVo;
import com.tramshedtech.eventmanagement.config.ExportExcel;
import com.tramshedtech.eventmanagement.entity.Department;
import com.tramshedtech.eventmanagement.entity.Position;
import com.tramshedtech.eventmanagement.entity.User;
import com.tramshedtech.eventmanagement.service.UserService;
import com.tramshedtech.eventmanagement.entity.CustomPage;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user, HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        System.out.println(user);

        // Access to user information by account number
        User result = userService.findByAccount(user.getAccount());


        // Creating Returned Objects
        ResponseResult responseResult = new ResponseResult();

        // Determine if it is empty
        if (result == null) {
            responseResult.setCode(401);  //401 Login Failed Not logged in
            responseResult.setMessage("Incorrect account number or password");
            responseResult.setStatus(ResponseStatus.ACCOUNT_OR_PASSWORD_ERROR);
            //
            return responseResult;
        }

        user.setPassword(DigestUtils.md5Hex(user.getPassword()));

        if (!result.getPassword().equals(user.getPassword())) {
            responseResult.setCode(401);  //401 Login Failed Not logged in
            responseResult.setMessage("Incorrect account number or password");
            responseResult.setStatus(ResponseStatus.ACCOUNT_OR_PASSWORD_ERROR);
            //
            return responseResult;
        }
        // Leave the password empty and do not return it to the front-end to prevent password leakage
        result.setPassword(null);


        // Store user id in session
        session.setAttribute("uid", result.getId());
        session.setAttribute("uname",result.getAccount());

        String token = UUID.randomUUID().toString();
        session.setAttribute("token", token);



        // The code executes here indicating that the login was successful and returns the user information to the front-end.
        responseResult.setCode(200);
        responseResult.setMessage("Login successful");
        responseResult.setStatus(ResponseStatus.LOGIN_SUCCESS);
        responseResult.setData(token);

        return responseResult;
    }

    @PostMapping("/regis")
    public ResponseResult regis(@RequestBody User user) throws SQLException {

        List<User> allUsers = userService.allUsers();

        for (User existingUser : allUsers) {
            if (existingUser.getAccount().toLowerCase().equals(user.getAccount().toLowerCase())) {
                return new ResponseResult().setCode(500).setStatus(ResponseStatus.ACCOUNT_OR_PASSWORD_ERROR);
            }
            if (existingUser.getEmail().toLowerCase().equals(user.getEmail().toLowerCase())) {
                return new ResponseResult().setCode(500).setStatus(ResponseStatus.EMAIL_ERROR);
            }
        }

        String passWord = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(passWord);
        System.out.println(user.getPassword());

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String format = dateFormat.format(date);
        user.setEntrydate(format);

        boolean r = userService.regis(user);

        if (r) {
            return new ResponseResult().setCode(200).setStatus(ResponseStatus.SUCCESS);
        }
        return new ResponseResult().setCode(500).setStatus(ResponseStatus.FAIL);
    }

    @GetMapping("/findAvatar")
    public ResponseResult findAvatar(HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        System.out.println(uid);

        String avatar = userService.findAvatar(uid);
        System.out.println(avatar);
        if(avatar != null) {
            return new ResponseResult().setCode(200).setStatus(ResponseStatus.SUCCESS).setData(avatar);
        }
        return new ResponseResult().setCode(500).setStatus(ResponseStatus.FAIL);
    }

    @GetMapping("/getUsername")
    public ResponseResult getUsername(HttpSession session) {
        String uname = (String) session.getAttribute("uname");

        if(uname != null) {
            return new ResponseResult().setCode(200).setStatus(ResponseStatus.SUCCESS).setData(uname);
        }
        return new ResponseResult().setCode(500).setStatus(ResponseStatus.FAIL);
    }

    @GetMapping("/getPassword/{pwd}")
    public boolean getPwd(@PathVariable("pwd") String pwd, HttpSession session){
        boolean r = false;
        int uid  = (int)session.getAttribute("uid");
        String passWord = DigestUtils.md5Hex(pwd);

        String confirmPassword = userService.getPwd(uid);
        System.out.println("p1="+passWord);
        System.out.println("p2="+confirmPassword);

        if(passWord.equals(confirmPassword)){
            r = true;
        } else if (!passWord.equals(confirmPassword)){
            r = false;
        }
        return r;
    }

    @PostMapping("/updatePwd/{pwd}")
    public boolean updatePwd(@PathVariable("pwd") String pwd, HttpSession session){

        int uid = (int) session.getAttribute("uid");

        String passWord = DigestUtils.md5Hex(pwd);
        boolean r = userService.updatePwd(passWord,uid);
        return r;
    }

    @GetMapping("/findbyId")
    public ResponseResult findbyId(HttpSession session){

        int uid  = (int)session.getAttribute("uid");
        System.out.println(uid);

        User u = userService.findbyId(uid);

        return new ResponseResult()
                .setCode(200)
                .setStatus(ResponseStatus.SUCCESS)
                .setData(u);
    }

    @PostMapping("/updateInfo")
    public boolean updateInfo(@RequestBody User user, HttpSession session){
        System.out.println(user);
        int uid = (int) session.getAttribute("uid");
        boolean r = userService.updateInfo(user,uid);
        return r;
    }

    @GetMapping("/findPositionDepartment")
    public ResponseResult findPosition(HttpSession session) {

        int uid = (int) session.getAttribute("uid");
        System.out.println(uid);

        UserPositionAndDepartmentVo positionAndDepartment = userService.findPositionDepartment(uid);

        System.out.println(positionAndDepartment);

        if(positionAndDepartment != null) {
            return new ResponseResult().setCode(200).setStatus(ResponseStatus.SUCCESS).setData(positionAndDepartment);
        }
        return new ResponseResult().setCode(500).setStatus(ResponseStatus.FAIL);
    }

    @GetMapping("/findAllUser/{page}/{size}")
    public ResponseResult<CustomPage> findAll(@PathVariable("page") int page, @PathVariable("size") int size){

        CustomPage customPage = userService.findAllUser(page, size);

        return new ResponseResult<CustomPage>().setCode(200).setData(customPage);
    }

    @GetMapping("/findAllDepartments")
    public ResponseResult<Department> findAllDepartment(){

        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setStatus(ResponseStatus.SUCCESS);
        responseResult.setData(userService.findAllDepartment());
        return responseResult;
    }

    @GetMapping("/findAllPositions")
    public ResponseResult<Position> findAllPosition(){

        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setStatus(ResponseStatus.SUCCESS);
        responseResult.setData(userService.findAllPosition());
        return responseResult;
    }

    @GetMapping("/search")
    public ResponseResult<CustomPage> search(HttpServletRequest request){

        User users = new User();
        users.setAccount(request.getParameter("account"));
        users.setDid(request.getParameter("did"));
        users.setPid(request.getParameter("pid"));
        users.setSex(request.getParameter("sex"));
        users.setEntrydate(request.getParameter("entrydate"));

        System.out.println(users);

        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        System.out.println(page);
        System.out.println(size);

        CustomPage pages = new CustomPage().setCurrentPage(page).setSize(size);

        if (users.getEntrydate().length()>10){
            String s = users.getEntrydate().substring(0,10);
            System.out.println(s);
            users.setEntrydate(s);
        } else {
            int a = users.getEntrydate().length();
            String s = users.getEntrydate().substring(0,a);
            users.setEntrydate(s);
        }

        System.out.println(users);

        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setStatus(ResponseStatus.SUCCESS);
        responseResult.setData(userService.search(pages,users));
        return responseResult;

    }

    @PostMapping("/addUser")
    public boolean addUser(@RequestBody UserVo user) throws ParseException {

        if(user.getSex().equals("1")){
            user.setSex("Male");
        } else if (user.getSex().equals("2")){
            user.setSex("Female");
        } else {
            user.setSex("None");
        }

        String passWord = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(passWord);

        System.out.println(user);

        boolean r = userService.addUser(user);
        return r;
    }

    @GetMapping("/findbyIdModify/{id}")
    public ResponseResult findbyIdModify(@PathVariable("id") int id){
        UserVo user = userService.findbyIdModify(id);

        return new ResponseResult()
                .setCode(200)
                .setStatus(ResponseStatus.SUCCESS)
                .setData(user);
    }

    @PostMapping("/findbyIdDel/{id}")
    public ResponseResult findbyIdDel(@PathVariable("id") int id){
        boolean r = userService.findbyIdDel(id);

        return new ResponseResult()
                .setCode(200)
                .setStatus(ResponseStatus.SUCCESS)
                .setData(r);
    }

    @PostMapping("/modifyUser")
    public boolean modifyUser(@RequestBody UserVo user) {

        if(user.getSex().equals("1")){
            user.setSex("Male");
        } else if (user.getSex().equals("2")){
            user.setSex("Female");
        } else {
            user.setSex("None");
        }


        boolean r = userService.modifyUser(user);
        return r;
    }

    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response) {

        // Query the database for the data to be exported
        List<User> userList = userService.allUsersExcel();

        // Create excel sheet header
        List<String> column = new ArrayList<>();
        column.add("id");
        column.add("Account");
        column.add("Email");
        column.add("Department");
        column.add("Position");
        column.add("Gender");
        column.add("Entrydate");
        column.add("Phone");
        column.add("Wechat");

        // Data corresponding to the table header
        List<Map<String, Object>> data = new ArrayList<>();

        // Iterate over the acquired data to be exported, k should be the same as the table header
        for (int i = 0; i < userList.size(); i++) {
            Map<String, Object> dataMap = new HashMap<>();
            User user = userList.get(i);
            dataMap.put("id", user.getId());
            dataMap.put("Account", user.getAccount());
            dataMap.put("Email", user.getEmail());
            dataMap.put("Department", user.getDid());
            dataMap.put("Position", user.getPid());
            dataMap.put("Gender", user.getSex());
            dataMap.put("Entrydate", user.getEntrydate());
            dataMap.put("Phone", user.getPhone());
            dataMap.put("Wechat", user.getWechat());

            data.add(dataMap);
        }

        // Calling the export tool class
        ExportExcel.exportExcel("UserList", column, data, request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.removeAttribute("uid");

        session.removeAttribute("uname");

        session.removeAttribute("token");

    }
}
