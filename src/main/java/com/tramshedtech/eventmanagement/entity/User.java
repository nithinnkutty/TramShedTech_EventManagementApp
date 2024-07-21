package com.tramshedtech.eventmanagement.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String account;
    private String password;
    private String email;
    private String avatar;
    private int status;
    private String did;
    private String pid;
    private String sex;
    private String birthday;
    private Long phone;
    private String wechat;
}
