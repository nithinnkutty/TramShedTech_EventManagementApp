package com.tramshedtech.eventmanagement.Vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    private int id;
    private String account;
    private String password;
    private String email;
    private String avatar;
    private int status;
    private String did;
    private String pid;
    private String sex;
    private Date entrydate;
    private String phone;
    private String wechat;
}
