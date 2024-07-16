package com.tramshedtech.eventmanagement.controller;



import com.tramshedtech.eventmanagement.entity.User;
import com.tramshedtech.eventmanagement.service.UserService;
import com.tramshedtech.eventmanagement.util.ResponseResult;
import com.tramshedtech.eventmanagement.util.ResponseStatus;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user, HttpSession session) throws SQLException {
        System.out.println(user);

        // Access to user information by account number
        User result = userService.findByAccount(user.getAccount());

        // Creating Returned Objects
        ResponseResult responseResult = new ResponseResult();

        // Determine if it is empty
        if (result == null || !result.getPassword().equals(user.getPassword())) {
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

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String format = dateFormat.format(date);


        // The code executes here indicating that the login was successful and returns the user information to the front-end.
        responseResult.setCode(200);
        responseResult.setMessage("Login successful");
        responseResult.setStatus(ResponseStatus.LOGIN_SUCCESS);
        responseResult.setData(result);

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

        boolean r = userService.regis(user);

        if (r) {
            return new ResponseResult().setCode(200).setStatus(ResponseStatus.SUCCESS);
        }
        return new ResponseResult().setCode(500).setStatus(ResponseStatus.FAIL);
    }



}
